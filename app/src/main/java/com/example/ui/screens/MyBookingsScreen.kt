package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookingEntity
import com.example.ui.theme.SolarGoldSecondary
import com.example.ui.theme.WaterBluePrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CleanCareViewModel

@Composable
fun MyBookingsScreen(
    viewModel: CleanCareViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val bookings by viewModel.bookings.collectAsState()

    var bookingToDelete by remember { mutableStateOf<BookingEntity?>(null) }

    if (bookingToDelete != null) {
        AlertDialog(
            onDismissRequest = { bookingToDelete = null },
            title = { Text("Cancel Booking?") },
            text = { Text("Are you sure you want to cancel booking #${bookingToDelete?.id}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        bookingToDelete?.let { viewModel.deleteBooking(it.id) }
                        bookingToDelete = null
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { bookingToDelete = null }) {
                    Text("Keep")
                }
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("my_bookings_screen"),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(WaterBluePrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "My Service Requests",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                        Text(
                            text = "${bookings.size} booking(s) on record",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        )
                    }
                }
            }
        }

        if (bookings.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "📋", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Bookings Yet",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "You haven't requested any cleaning services yet. Book water tank cleaning or solar plate wash in just 1 minute!",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.openBooking() },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WaterBluePrimary)
                        ) {
                            Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Book a Service Now")
                        }
                    }
                }
            }
        } else {
            items(bookings) { booking ->
                BookingItemCard(
                    booking = booking,
                    onWhatsAppClick = { viewModel.openWhatsAppBooking(context, booking) },
                    onCallClick = { viewModel.callBusiness(context) },
                    onDeleteClick = { bookingToDelete = booking },
                    onStatusChange = { newStatus -> viewModel.updateBookingStatus(booking.id, newStatus) }
                )
            }
        }
    }
}

@Composable
fun BookingItemCard(
    booking: BookingEntity,
    onWhatsAppClick: () -> Unit,
    onCallClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onStatusChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = when (booking.status) {
                        "Confirmed" -> Color(0xFFD1FAE5)
                        "Completed" -> Color(0xFFE0E7FF)
                        else -> Color(0xFFFEF3C7)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "● ${booking.status.uppercase()}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = when (booking.status) {
                                "Confirmed" -> Color(0xFF065F46)
                                "Completed" -> Color(0xFF3730A3)
                                else -> Color(0xFF92400E)
                            }
                        )
                    )
                }

                Text(
                    text = "#${booking.id}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = booking.requiredService,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Details: ${booking.tankSizeDetails}",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(14.dp), tint = WaterBluePrimary)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${booking.preferredDate} • ${booking.preferredTime}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                )
            }

            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = booking.address,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    maxLines = 1
                )
            }

            if (booking.additionalMessage.isNotBlank()) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "Note: ${booking.additionalMessage}",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onWhatsAppClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = Color(0xFF25D366))
                }

                IconButton(
                    onClick = onCallClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = "Call", tint = WaterBluePrimary)
                }

                Spacer(modifier = Modifier.weight(1f))

                // Toggle status shortcut
                TextButton(
                    onClick = {
                        val next = when (booking.status) {
                            "Requested" -> "Confirmed"
                            "Confirmed" -> "Completed"
                            else -> "Requested"
                        }
                        onStatusChange(next)
                    }
                ) {
                    Text("Update Status", fontSize = 12.sp)
                }

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}
