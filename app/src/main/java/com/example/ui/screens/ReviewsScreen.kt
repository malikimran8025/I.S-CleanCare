package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ReviewEntity
import com.example.ui.components.RatingStars
import com.example.ui.theme.SolarGoldSecondary
import com.example.ui.theme.WaterBluePrimary
import com.example.ui.viewmodel.CleanCareViewModel

@Composable
fun ReviewsScreen(
    viewModel: CleanCareViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val reviews by viewModel.reviews.collectAsState()
    val showDialog by viewModel.showReviewDialog.collectAsState()
    val reviewForm by viewModel.reviewForm.collectAsState()

    var selectedFilter by remember { mutableStateOf("All") }

    val filteredReviews = remember(reviews, selectedFilter) {
        if (selectedFilter == "All") reviews
        else reviews.filter { it.serviceUsed.contains(selectedFilter, ignoreCase = true) }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.setShowReviewDialog(false) },
            title = {
                Text(
                    text = "Share Your Experience",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Your feedback helps us maintain our high service standard in Faisal Town.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    OutlinedTextField(
                        value = reviewForm.reviewerName,
                        onValueChange = { viewModel.updateReviewName(it) },
                        label = { Text("Your Name *") },
                        placeholder = { Text("e.g. Asim Raza") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = reviewForm.location,
                        onValueChange = { viewModel.updateReviewLocation(it) },
                        label = { Text("Area / Block") },
                        placeholder = { Text("e.g. Faisal Town, B Block") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Rating:", fontWeight = FontWeight.SemiBold)
                        RatingStars(
                            rating = reviewForm.rating,
                            starSize = 22,
                            onRatingChanged = { viewModel.updateReviewRating(it) }
                        )
                    }

                    OutlinedTextField(
                        value = reviewForm.comment,
                        onValueChange = { viewModel.updateReviewComment(it) },
                        label = { Text("Your Review *") },
                        placeholder = { Text("How was the cleaning quality, punctuality, and staff behavior?") },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.submitReview(context) },
                    colors = ButtonDefaults.buttonColors(containerColor = WaterBluePrimary)
                ) {
                    Text("Submit Review")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.setShowReviewDialog(false) }) {
                    Text("Cancel")
                }
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("reviews_screen"),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Ratings Summary Hero
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "4.9",
                                style = MaterialTheme.typography.displayMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = WaterBluePrimary
                                )
                            )
                            RatingStars(rating = 5, starSize = 18)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Based on ${reviews.size}+ verified customers",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }

                        Button(
                            onClick = { viewModel.setShowReviewDialog(true) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SolarGoldSecondary)
                        ) {
                            Icon(Icons.Default.RateReview, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Write Review", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )

                    // Trust indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TrustBadge(label = "100%", sub = "Satisfaction")
                        TrustBadge(label = "100%", sub = "Verified Local")
                        TrustBadge(label = "0", sub = "Hidden Costs")
                    }
                }
            }
        }

        // Filter chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == "All",
                    onClick = { selectedFilter = "All" },
                    label = { Text("All (${reviews.size})") }
                )
                FilterChip(
                    selected = selectedFilter == "Water Tank",
                    onClick = { selectedFilter = "Water Tank" },
                    label = { Text("Water Tank") }
                )
                FilterChip(
                    selected = selectedFilter == "Solar Plate",
                    onClick = { selectedFilter = "Solar Plate" },
                    label = { Text("Solar Plate") }
                )
            }
        }

        // Review Items
        items(filteredReviews) { review ->
            CustomerReviewCard(review = review)
        }
    }
}

@Composable
fun CustomerReviewCard(review: ReviewEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = review.reviewerName.firstOrNull()?.uppercase() ?: "U",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = review.reviewerName,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            if (review.isVerified) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified Client",
                                    tint = WaterBluePrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Text(
                            text = "📍 ${review.location} • ${review.serviceUsed}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                RatingStars(rating = review.rating, starSize = 14)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.reviewDate,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@Composable
fun TrustBadge(label: String, sub: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontWeight = FontWeight.ExtraBold,
            color = WaterBluePrimary,
            fontSize = 16.sp
        )
        Text(
            text = sub,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
