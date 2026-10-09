package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BookingEntity
import com.example.data.local.CleanCareDatabase
import com.example.data.local.ReviewEntity
import com.example.data.model.BusinessConfig
import com.example.data.model.CleaningStep
import com.example.data.model.FaqItem
import com.example.data.model.ServicePackage
import com.example.data.repository.CleanCareRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class AppScreen {
    object Home : AppScreen()
    object Services : AppScreen()
    object WaterTankDetail : AppScreen()
    object SolarWashDetail : AppScreen()
    data class Booking(val preselectedService: String? = null, val packageTitle: String? = null) : AppScreen()
    object MyBookings : AppScreen()
    object HowItWorks : AppScreen()
    object AboutUs : AppScreen()
    object CustomerReviews : AppScreen()
    object ContactUs : AppScreen()
}

data class BookingFormState(
    val customerName: String = "",
    val phoneNumber: String = "",
    val address: String = "Faisal Town, ",
    val requiredService: String = "Water Tank Cleaning",
    val tankSizeOrDetails: String = "",
    val preferredDate: String = "",
    val preferredTime: String = "Morning (08:00 AM – 11:00 AM)",
    val additionalMessage: String = "",
    val isSubmitting: Boolean = false,
    val submitSuccessBookingId: Long? = null,
    val errorMessage: String? = null
)

data class ReviewFormState(
    val reviewerName: String = "",
    val location: String = "Faisal Town",
    val rating: Int = 5,
    val serviceUsed: String = "Water Tank Cleaning",
    val comment: String = "",
    val isSubmitting: Boolean = false
)

class CleanCareViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CleanCareRepository

    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.Home)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _screenHistory = mutableListOf<AppScreen>()

    private val _bookingForm = MutableStateFlow(BookingFormState())
    val bookingForm: StateFlow<BookingFormState> = _bookingForm.asStateFlow()

    private val _reviewForm = MutableStateFlow(ReviewFormState())
    val reviewForm: StateFlow<ReviewFormState> = _reviewForm.asStateFlow()

    private val _showReviewDialog = MutableStateFlow(false)
    val showReviewDialog: StateFlow<Boolean> = _showReviewDialog.asStateFlow()

    val bookings: StateFlow<List<BookingEntity>>
    val reviews: StateFlow<List<ReviewEntity>>

    init {
        val database = CleanCareDatabase.getDatabase(application)
        repository = CleanCareRepository(database.cleanCareDao())

        bookings = repository.allBookings.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        reviews = repository.allReviews.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        viewModelScope.launch {
            repository.ensureInitialData()
        }

        // Initialize preferred date with tomorrow's date
        initDefaultDate()
    }

    private fun initDefaultDate() {
        try {
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val tomorrow = Date(System.currentTimeMillis() + 86400000L)
            _bookingForm.value = _bookingForm.value.copy(preferredDate = sdf.format(tomorrow))
        } catch (_: Exception) {}
    }

    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            _screenHistory.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        return if (_screenHistory.isNotEmpty()) {
            val prev = _screenHistory.removeAt(_screenHistory.size - 1)
            _currentScreen.value = prev
            true
        } else if (_currentScreen.value !is AppScreen.Home) {
            _currentScreen.value = AppScreen.Home
            true
        } else {
            false
        }
    }

    fun openBooking(preselectedService: String? = null, packageTitle: String? = null) {
        val updatedService = when (preselectedService) {
            "Water Tank Cleaning" -> "Water Tank Cleaning"
            "Solar Plate Wash" -> "Solar Plate Wash"
            "Complete Combo Pack" -> "Complete Combo Pack"
            else -> _bookingForm.value.requiredService
        }

        val details = if (!packageTitle.isNullOrBlank()) {
            "Package: $packageTitle"
        } else {
            _bookingForm.value.tankSizeOrDetails
        }

        _bookingForm.value = _bookingForm.value.copy(
            requiredService = updatedService,
            tankSizeOrDetails = details,
            submitSuccessBookingId = null,
            errorMessage = null
        )
        navigateTo(AppScreen.Booking(preselectedService, packageTitle))
    }

    fun updateBookingName(name: String) {
        _bookingForm.value = _bookingForm.value.copy(customerName = name, errorMessage = null)
    }

    fun updateBookingPhone(phone: String) {
        _bookingForm.value = _bookingForm.value.copy(phoneNumber = phone, errorMessage = null)
    }

    fun updateBookingAddress(address: String) {
        _bookingForm.value = _bookingForm.value.copy(address = address, errorMessage = null)
    }

    fun updateBookingService(service: String) {
        _bookingForm.value = _bookingForm.value.copy(requiredService = service)
    }

    fun updateBookingDetails(details: String) {
        _bookingForm.value = _bookingForm.value.copy(tankSizeOrDetails = details)
    }

    fun updateBookingDate(date: String) {
        _bookingForm.value = _bookingForm.value.copy(preferredDate = date)
    }

    fun updateBookingTime(time: String) {
        _bookingForm.value = _bookingForm.value.copy(preferredTime = time)
    }

    fun updateBookingMessage(message: String) {
        _bookingForm.value = _bookingForm.value.copy(additionalMessage = message)
    }

    fun submitBooking(context: Context, alsoOpenWhatsApp: Boolean = false) {
        val current = _bookingForm.value
        if (current.customerName.trim().isEmpty()) {
            _bookingForm.value = current.copy(errorMessage = "Please enter your name")
            return
        }
        if (current.phoneNumber.trim().length < 8) {
            _bookingForm.value = current.copy(errorMessage = "Please enter a valid phone number")
            return
        }
        if (current.address.trim().isEmpty()) {
            _bookingForm.value = current.copy(errorMessage = "Please enter your service address")
            return
        }

        viewModelScope.launch {
            _bookingForm.value = current.copy(isSubmitting = true, errorMessage = null)
            val entity = BookingEntity(
                customerName = current.customerName.trim(),
                phoneNumber = current.phoneNumber.trim(),
                address = current.address.trim(),
                requiredService = current.requiredService,
                tankSizeDetails = if (current.tankSizeOrDetails.isBlank()) "Standard" else current.tankSizeOrDetails.trim(),
                preferredDate = current.preferredDate.ifBlank { "As soon as possible" },
                preferredTime = current.preferredTime,
                additionalMessage = current.additionalMessage.trim(),
                status = "Requested"
            )

            val bookingId = repository.addBooking(entity)
            _bookingForm.value = current.copy(
                isSubmitting = false,
                submitSuccessBookingId = bookingId
            )

            Toast.makeText(context, "Booking requested successfully!", Toast.LENGTH_SHORT).show()

            if (alsoOpenWhatsApp) {
                openWhatsAppBooking(context, entity)
            }
        }
    }

    fun resetBookingForm() {
        _bookingForm.value = BookingFormState()
        initDefaultDate()
    }

    fun updateBookingStatus(id: Long, newStatus: String) {
        viewModelScope.launch {
            repository.updateBookingStatus(id, newStatus)
        }
    }

    fun deleteBooking(id: Long) {
        viewModelScope.launch {
            repository.deleteBooking(id)
        }
    }

    // Customer review submission
    fun setShowReviewDialog(show: Boolean) {
        _showReviewDialog.value = show
    }

    fun updateReviewName(name: String) {
        _reviewForm.value = _reviewForm.value.copy(reviewerName = name)
    }

    fun updateReviewLocation(loc: String) {
        _reviewForm.value = _reviewForm.value.copy(location = loc)
    }

    fun updateReviewRating(rating: Int) {
        _reviewForm.value = _reviewForm.value.copy(rating = rating)
    }

    fun updateReviewService(service: String) {
        _reviewForm.value = _reviewForm.value.copy(serviceUsed = service)
    }

    fun updateReviewComment(comment: String) {
        _reviewForm.value = _reviewForm.value.copy(comment = comment)
    }

    fun submitReview(context: Context) {
        val form = _reviewForm.value
        if (form.reviewerName.isBlank() || form.comment.isBlank()) {
            Toast.makeText(context, "Please enter your name and review comment", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            val newReview = ReviewEntity(
                reviewerName = form.reviewerName.trim(),
                location = form.location.trim().ifBlank { "Faisal Town" },
                rating = form.rating,
                serviceUsed = form.serviceUsed,
                comment = form.comment.trim(),
                reviewDate = "Just now",
                isVerified = true
            )
            repository.addReview(newReview)
            _showReviewDialog.value = false
            _reviewForm.value = ReviewFormState()
            Toast.makeText(context, "Thank you for your review!", Toast.LENGTH_SHORT).show()
        }
    }

    // Intents & Actions
    fun callBusiness(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${BusinessConfig.PRIMARY_PHONE}")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Unable to launch phone dialer", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsAppChat(context: Context, customText: String? = null) {
        try {
            val message = customText ?: "Hello I.S CleanCare! I would like to inquire about your water tank cleaning & solar plate wash services in Faisal Town."
            val encoded = URLEncoder.encode(message, "UTF-8")
            val url = "https://api.whatsapp.com/send?phone=${BusinessConfig.WHATSAPP_PHONE}&text=$encoded"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Unable to open WhatsApp. Please call ${BusinessConfig.DISPLAY_PHONE}", Toast.LENGTH_LONG).show()
        }
    }

    fun openWhatsAppBooking(context: Context, booking: BookingEntity) {
        val text = """
            *NEW BOOKING REQUEST - I.S CleanCare*
            👤 *Customer:* ${booking.customerName}
            📞 *Phone:* ${booking.phoneNumber}
            📍 *Address:* ${booking.address}
            🛠️ *Service:* ${booking.requiredService}
            📦 *Details/Size:* ${booking.tankSizeDetails}
            📅 *Date:* ${booking.preferredDate}
            ⏰ *Time Slot:* ${booking.preferredTime}
            📝 *Note:* ${booking.additionalMessage.ifBlank { "None" }}
            
            _Please confirm availability and quote._
        """.trimIndent()
        openWhatsAppChat(context, text)
    }

    fun openMapLocation(context: Context) {
        try {
            val uri = Uri.parse("geo:0,0?q=Faisal+Town+B+Block+Lahore")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Location: ${BusinessConfig.FULL_ADDRESS}", Toast.LENGTH_LONG).show()
        }
    }

    fun shareApp(context: Context) {
        try {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, BusinessConfig.APP_BRAND_NAME)
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out ${BusinessConfig.BUSINESS_NAME} for professional Water Tank Cleaning & Solar Plate Wash in Faisal Town, Lahore! Call/WhatsApp: ${BusinessConfig.DISPLAY_PHONE}"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share I.S CleanCare")
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(shareIntent)
        } catch (_: Exception) {}
    }

    fun getServicePackages(): List<ServicePackage> = repository.getServicePackages()
    fun getWaterTankSteps(): List<CleaningStep> = repository.getWaterTankSteps()
    fun getSolarWashSteps(): List<CleaningStep> = repository.getSolarWashSteps()
    fun getFaqs(): List<FaqItem> = repository.getFaqs()
}
