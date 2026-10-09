package com.example.data.repository

import com.example.data.local.BookingEntity
import com.example.data.local.CleanCareDao
import com.example.data.local.ReviewEntity
import com.example.data.model.CleaningStep
import com.example.data.model.FaqItem
import com.example.data.model.ServicePackage
import kotlinx.coroutines.flow.Flow

class CleanCareRepository(private val dao: CleanCareDao) {

    val allBookings: Flow<List<BookingEntity>> = dao.getAllBookings()
    val allReviews: Flow<List<ReviewEntity>> = dao.getAllReviews()

    suspend fun addBooking(booking: BookingEntity): Long {
        return dao.insertBooking(booking)
    }

    suspend fun updateBookingStatus(id: Long, status: String) {
        dao.updateBookingStatus(id, status)
    }

    suspend fun deleteBooking(id: Long) {
        dao.deleteBooking(id)
    }

    suspend fun addReview(review: ReviewEntity): Long {
        return dao.insertReview(review)
    }

    suspend fun ensureInitialData() {
        if (dao.getReviewCount() == 0) {
            val initialReviews = listOf(
                ReviewEntity(
                    reviewerName = "Haji Muhammad Aslam",
                    location = "Faisal Town, B Block",
                    rating = 5,
                    serviceUsed = "Water Tank Cleaning",
                    comment = "Excellent service! They completely removed 2 years of mud and sludge from our underground tank. Water is crystal clear now. Very courteous team.",
                    reviewDate = "2 days ago"
                ),
                ReviewEntity(
                    reviewerName = "Kamran Siddiqui",
                    location = "Faisal Town, B Block",
                    rating = 5,
                    serviceUsed = "Solar Plate Wash",
                    comment = "Solar generation increased by almost 28% right after the wash! They washed all 16 plates carefully without scratching. Highly recommended in Faisal Town.",
                    reviewDate = "1 week ago"
                ),
                ReviewEntity(
                    reviewerName = "Dr. Tariq Mehmood",
                    location = "Model Town, Lahore",
                    rating = 5,
                    serviceUsed = "Tank & Solar Combo",
                    comment = "Got both overhead tank and 24 solar plates cleaned. Punctual, professional pressure equipment, and very reasonable charges. Will book regularly.",
                    reviewDate = "2 weeks ago"
                ),
                ReviewEntity(
                    reviewerName = "Mrs. Farooq",
                    location = "Johar Town, Block G",
                    rating = 5,
                    serviceUsed = "Water Tank Cleaning",
                    comment = "Very hygienic and tidy work. The staff wore clean boots and used anti-bacterial treatment for our drinking water tank. 10/10 service.",
                    reviewDate = "3 weeks ago"
                )
            )
            dao.insertAllReviews(initialReviews)
        }
    }

    fun getServicePackages(): List<ServicePackage> {
        return listOf(
            ServicePackage(
                id = "wt_standard",
                title = "Standard Overhead Tank Clean",
                serviceType = "Water Tank Cleaning",
                suitableFor = "500 to 1000 Gallon Tank",
                priceEstimate = "Rs. 2,500 – 3,500",
                durationHours = "1.5 – 2 Hours",
                keyFeatures = listOf(
                    "Sludge, mud & sediment drainage",
                    "High-pressure motorized jet wash",
                    "Anti-fungal scrubbing",
                    "UV/Chlorine antibacterial rinse",
                    "Clean inspection certificate"
                ),
                isPopular = false
            ),
            ServicePackage(
                id = "wt_deep",
                title = "Underground Deep Hygiene Wash",
                serviceType = "Water Tank Cleaning",
                suitableFor = "1500 to 3000 Gallon Tank",
                priceEstimate = "Rs. 4,500 – 6,500",
                durationHours = "2.5 – 3.5 Hours",
                keyFeatures = listOf(
                    "Submersible heavy de-watering pump",
                    "Deep manual sludge extraction",
                    "Rotary brush wall descaling",
                    "Medical-grade disinfectant spray",
                    "Complete pipe flushing",
                    "Water clarity guarantee"
                ),
                isPopular = true
            ),
            ServicePackage(
                id = "sp_standard",
                title = "Residential Solar Plate Wash",
                serviceType = "Solar Plate Wash",
                suitableFor = "Up to 16 Solar Panels (5kW–10kW)",
                priceEstimate = "Rs. 2,000 – 3,000",
                durationHours = "1 Hour",
                keyFeatures = listOf(
                    "Non-scratch soft bristle microfiber wash",
                    "Purified demineralized water rinse",
                    "Dust, smog & bird dropping removal",
                    "Panel surface safety check",
                    "Instant boost in solar generation"
                ),
                isPopular = false
            ),
            ServicePackage(
                id = "combo_saver",
                title = "Ultimate Home Combo Care",
                serviceType = "Complete Combo Pack",
                suitableFor = "Water Tank + Up to 20 Solar Panels",
                priceEstimate = "Rs. 5,000 – 7,500",
                durationHours = "3 – 4 Hours",
                keyFeatures = listOf(
                    "Full Underground or Overhead Tank wash",
                    "Complete Solar Panels wash & streak-free dry",
                    "Priority scheduling same day",
                    "Free roof gutter debris sweep",
                    "Save up to 25% on combined service"
                ),
                isPopular = true
            )
        )
    }

    fun getWaterTankSteps(): List<CleaningStep> {
        return listOf(
            CleaningStep(
                stepNumber = 1,
                title = "Draining & Emptying",
                description = "Our team drains residual dirty water using specialized submersible suction pumps to reach the bottom floor.",
                iconEmoji = "🌊"
            ),
            CleaningStep(
                stepNumber = 2,
                title = "Sludge & Sediment Removal",
                description = "Thick layers of mud, silt, algae, and deposits are systematically vacuumed and scooped out safely.",
                iconEmoji = "🧹"
            ),
            CleaningStep(
                stepNumber = 3,
                title = "High-Pressure Jet Scrubbing",
                description = "Using powerful pressure washers, all wall surfaces, ceilings, and corners are scrubbed clean without surface damage.",
                iconEmoji = "💧"
            ),
            CleaningStep(
                stepNumber = 4,
                title = "Antibacterial Disinfection",
                description = "Safe food-grade anti-bacterial solution is applied to eradicate dangerous bacteria, fungi, and pathogens.",
                iconEmoji = "🛡️"
            ),
            CleaningStep(
                stepNumber = 5,
                title = "Fresh Water Rinse & Inspection",
                description = "Final rinse with fresh water and complete vacuum drying so your tank is immediately ready for clean water filling.",
                iconEmoji = "✨"
            )
        )
    }

    fun getSolarWashSteps(): List<CleaningStep> {
        return listOf(
            CleaningStep(
                stepNumber = 1,
                title = "Inspection & Safety Check",
                description = "Technicians verify panel temperatures and check wiring, glass condition, and framing before washing.",
                iconEmoji = "🔍"
            ),
            CleaningStep(
                stepNumber = 2,
                title = "Debris & Dry Dust Clearing",
                description = "Leaves, twigs, and loose dry debris are brushed away safely using soft anti-static tools.",
                iconEmoji = "🍃"
            ),
            CleaningStep(
                stepNumber = 3,
                title = "Gentle Microfiber Wash",
                description = "Safe pH-neutral water spray and telescopic soft bristles remove stubborn bird droppings and baked smog.",
                iconEmoji = "☀️"
            ),
            CleaningStep(
                stepNumber = 4,
                title = "Demineralized Spot-Free Rinse",
                description = "Panels are rinsed with pure water to prevent calcium lime spots and ensure maximum sunlight transmission.",
                iconEmoji = "✨"
            )
        )
    }

    fun getFaqs(): List<FaqItem> {
        return listOf(
            FaqItem(
                question = "How often should I get my water tank cleaned?",
                answer = "Health experts recommend cleaning drinking and domestic water tanks at least once every 6 months to prevent harmful algae, bacterial buildup, and sediment accumulation."
            ),
            FaqItem(
                question = "Why is professional solar panel washing necessary?",
                answer = "In urban and dusty areas like Lahore, dust, bird droppings, and smog can decrease solar efficiency by 20% to 35%. Regular washing with non-abrasive tools protects the glass coating and boosts daily unit production."
            ),
            FaqItem(
                question = "Do you bring your own cleaning equipment?",
                answer = "Yes! I.S CleanCare brings professional pressure washers, de-watering pumps, telescopic solar wash poles, and safe disinfectant supplies."
            ),
            FaqItem(
                question = "Which areas do you serve?",
                answer = "We are based in Faisal Town, B Block, and serve all surrounding areas including Model Town, Johar Town, Garden Town, Township, Faisal Town, and Gulberg."
            )
        )
    }
}
