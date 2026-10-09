package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerName: String,
    val phoneNumber: String,
    val address: String,
    val requiredService: String, // "Water Tank Cleaning", "Solar Plate Wash", "Complete Combo Pack"
    val tankSizeDetails: String, // e.g. "Overhead 800 Gallons", "16 Solar Plates"
    val preferredDate: String,
    val preferredTime: String,
    val additionalMessage: String,
    val createdAt: Long = System.currentTimeMillis(),
    val status: String = "Requested" // "Requested", "Confirmed", "Completed"
)

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reviewerName: String,
    val location: String,
    val rating: Int, // 1 to 5
    val serviceUsed: String,
    val comment: String,
    val reviewDate: String,
    val isVerified: Boolean = true
)
