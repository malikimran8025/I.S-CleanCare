package com.example.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BusinessConfig
import com.example.ui.theme.SolarGoldSecondary
import com.example.ui.theme.WaterBluePrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CleanCareViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    viewModel: CleanCareViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val formState by viewModel.bookingForm.collectAsState()

    val serviceOptions = listOf(
        "Water Tank Cleaning",
        "Solar Plate Wash",
        "Complete Combo Pack"
    )

    val timeOptions = listOf(
        "Morning (08:00 AM – 11:00 AM)",
        "Midday (11:00 AM – 02:00 PM)",
        "Afternoon (02:00 PM – 05:00 PM)",
        "Evening (05:00 PM – 07:00 PM)"
    )

    var showServiceDropdown by remember { mutableStateOf(false) }
    var showTimeDropdown by remember { mutableStateOf(false) }

    // Date picker dialog
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
                val formatted = String.format("%02d %s %d", dayOfMonth, months[month], year)
                viewModel.updateBookingDate(formatted)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("booking_screen"),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "📅", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Book a Cleaning Service",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Fill in your details below. Our team in Faisal Town, B Block will confirm your slot promptly.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    )
                }
            }
        }

        // Success Confirmation Card if submitted
        if (formState.submitSuccessBookingId != null) {
            item {
                Card(
                    modifier = Modifier.testTag("booking_success_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFECFDF5)
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF10B981))
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Booking Request Submitted!",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF065F46)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Booking Ref #${formState.submitSuccessBookingId} for ${formState.customerName}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF047857))
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    val entity = com.example.data.local.BookingEntity(
                                        id = formState.submitSuccessBookingId ?: 0,
                                        customerName = formState.customerName,
                                        phoneNumber = formState.phoneNumber,
                                        address = formState.address,
                                        requiredService = formState.requiredService,
                                        tankSizeDetails = formState.tankSizeOrDetails,
                                        preferredDate = formState.preferredDate,
                                        preferredTime = formState.preferredTime,
                                        additionalMessage = formState.additionalMessage
                                    )
                                    viewModel.openWhatsAppBooking(context, entity)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Send on WhatsApp", fontSize = 12.sp)
                            }

                            Button(
                                onClick = { viewModel.callBusiness(context) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = WaterBluePrimary)
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Call Now", fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { viewModel.resetBookingForm() }) {
                            Text("Book Another Service", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Error Banner
        if (formState.errorMessage != null) {
            item {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = formState.errorMessage ?: "",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        )
                    }
                }
            }
        }

        // Form Fields Container Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // 1. Customer Name
                    OutlinedTextField(
                        value = formState.customerName,
                        onValueChange = { viewModel.updateBookingName(it) },
                        label = { Text("Customer Name *") },
                        placeholder = { Text("e.g. Imran Malik") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_customer_name"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // 2. Phone Number
                    OutlinedTextField(
                        value = formState.phoneNumber,
                        onValueChange = { viewModel.updateBookingPhone(it) },
                        label = { Text("Phone Number *") },
                        placeholder = { Text("e.g. 0300-1234567") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_phone_number"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // 3. Address
                    OutlinedTextField(
                        value = formState.address,
                        onValueChange = { viewModel.updateBookingAddress(it) },
                        label = { Text("Service Address *") },
                        placeholder = { Text("House #, Street #, Faisal Town B Block") },
                        leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_address"),
                        minLines = 2,
                        maxLines = 3,
                        shape = RoundedCornerShape(12.dp)
                    )

                    // 4. Required Service Dropdown
                    ExposedDropdownMenuBox(
                        expanded = showServiceDropdown,
                        onExpandedChange = { showServiceDropdown = it }
                    ) {
                        OutlinedTextField(
                            value = formState.requiredService,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Required Service *") },
                            leadingIcon = { Icon(Icons.Default.CleaningServices, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showServiceDropdown) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("select_service"),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = showServiceDropdown,
                            onDismissRequest = { showServiceDropdown = false }
                        ) {
                            serviceOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        viewModel.updateBookingService(option)
                                        showServiceDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    // 5. Tank Size / Service Details
                    OutlinedTextField(
                        value = formState.tankSizeOrDetails,
                        onValueChange = { viewModel.updateBookingDetails(it) },
                        label = { Text("Tank Size / Service Details") },
                        placeholder = { Text("e.g. 1000 Gallon Underground / 16 Solar Panels") },
                        leadingIcon = { Icon(Icons.Default.Straighten, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_tank_size_details"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // 6. Preferred Date
                    OutlinedTextField(
                        value = formState.preferredDate,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Preferred Date *") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { datePickerDialog.show() }) {
                                Icon(Icons.Default.EditCalendar, contentDescription = "Pick Date")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog.show() }
                            .testTag("input_preferred_date"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // 7. Preferred Time Slot Dropdown
                    ExposedDropdownMenuBox(
                        expanded = showTimeDropdown,
                        onExpandedChange = { showTimeDropdown = it }
                    ) {
                        OutlinedTextField(
                            value = formState.preferredTime,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Preferred Time Slot *") },
                            leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showTimeDropdown) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("select_time_slot"),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = showTimeDropdown,
                            onDismissRequest = { showTimeDropdown = false }
                        ) {
                            timeOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        viewModel.updateBookingTime(option)
                                        showTimeDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    // 8. Additional Message
                    OutlinedTextField(
                        value = formState.additionalMessage,
                        onValueChange = { viewModel.updateBookingMessage(it) },
                        label = { Text("Additional Message / Instructions") },
                        placeholder = { Text("e.g. Please bring extra hose pipe, roof ladder available...") },
                        leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_additional_message"),
                        minLines = 2,
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Submit Booking Button
                    Button(
                        onClick = { viewModel.submitBooking(context, alsoOpenWhatsApp = false) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("submit_booking_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WaterBluePrimary),
                        enabled = !formState.isSubmitting
                    ) {
                        if (formState.isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Submit Booking",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    // Direct WhatsApp submit shortcut
                    OutlinedButton(
                        onClick = { viewModel.submitBooking(context, alsoOpenWhatsApp = true) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_and_whatsapp_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF25D366)
                        )
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Submit & Confirm via WhatsApp",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Quick Help Note
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = WaterBluePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Need emergency water tank emptying or urgent same-day service? Call us directly at ${BusinessConfig.DISPLAY_PHONE}.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}
