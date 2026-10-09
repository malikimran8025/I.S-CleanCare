package com.example.data.model

data class ServicePackage(
    val id: String,
    val title: String,
    val serviceType: String, // "Water Tank", "Solar Plate", "Combo"
    val suitableFor: String, // e.g., "500–1000 Gallons" or "Up to 12 Solar Plates"
    val priceEstimate: String,
    val durationHours: String,
    val keyFeatures: List<String>,
    val isPopular: Boolean = false
)

data class CleaningStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val iconEmoji: String
)

data class FaqItem(
    val question: String,
    val answer: String
)

object BusinessConfig {
    const val BUSINESS_NAME = "I.S Water Tank Cleaner & Solar Plate Wash"
    const val APP_BRAND_NAME = "I.S CleanCare"
    const val TAGLINE = "Water Tank Cleaning & Solar Plate Wash"
    const val LOCATION_LINE = "Faisal Town, B Block"
    const val FULL_ADDRESS = "Plot # B-Block, Near Main Market, Faisal Town, Lahore"
    const val PRIMARY_PHONE = "+923004567890" // Standard phone number for direct dial
    const val WHATSAPP_PHONE = "923004567890" // Number for WhatsApp API without + or spaces
    const val DISPLAY_PHONE = "+92 300 4567890"
    const val WORKING_HOURS = "Monday – Sunday: 7:00 AM – 7:00 PM"
    const val SERVICE_AREAS = "Faisal Town, Model Town, Johar Town, Garden Town, Township, Faisal Town B-Block, Gulberg & Nearby Areas"
}
