package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BusinessConfig
import com.example.ui.theme.SolarGoldSecondary
import com.example.ui.theme.WaterBluePrimary
import com.example.ui.viewmodel.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CleanCareTopAppBar(
    currentScreen: AppScreen,
    onBackClick: () -> Unit,
    onCallClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    onBookingsClick: () -> Unit,
    bookingCount: Int,
    modifier: Modifier = Modifier
) {
    val title = when (currentScreen) {
        is AppScreen.Home -> BusinessConfig.APP_BRAND_NAME
        is AppScreen.Services -> "Our Services"
        is AppScreen.WaterTankDetail -> "Water Tank Cleaning"
        is AppScreen.SolarWashDetail -> "Solar Plate Wash"
        is AppScreen.Booking -> "Book a Service"
        is AppScreen.MyBookings -> "My Bookings"
        is AppScreen.HowItWorks -> "How It Works"
        is AppScreen.AboutUs -> "About Us"
        is AppScreen.CustomerReviews -> "Customer Reviews"
        is AppScreen.ContactUs -> "Contact & Location"
    }

    val isHome = currentScreen is AppScreen.Home

    TopAppBar(
        modifier = modifier.testTag("app_top_bar"),
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (isHome) {
                    Text(
                        text = "Faisal Town, B Block",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        },
        navigationIcon = {
            if (!isHome) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("nav_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 4.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(WaterBluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💧",
                        fontSize = 18.sp
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = onWhatsAppClick,
                modifier = Modifier.testTag("topbar_whatsapp_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "WhatsApp",
                    tint = Color(0xFF25D366)
                )
            }
            IconButton(
                onClick = onCallClick,
                modifier = Modifier.testTag("topbar_call_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Call Now",
                    tint = WaterBluePrimary
                )
            }
            IconButton(
                onClick = onBookingsClick,
                modifier = Modifier.testTag("topbar_bookings_button")
            ) {
                BadgedBox(
                    badge = {
                        if (bookingCount > 0) {
                            Badge(containerColor = SolarGoldSecondary) {
                                Text(
                                    text = "$bookingCount",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = "My Bookings"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun CleanCareBottomNavigationBar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.testTag("bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        windowInsets = WindowInsets.navigationBars
    ) {
        NavigationBarItem(
            modifier = Modifier.testTag("bottom_nav_home"),
            selected = currentScreen is AppScreen.Home,
            onClick = { onNavigate(AppScreen.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            modifier = Modifier.testTag("bottom_nav_services"),
            selected = currentScreen is AppScreen.Services ||
                    currentScreen is AppScreen.WaterTankDetail ||
                    currentScreen is AppScreen.SolarWashDetail,
            onClick = { onNavigate(AppScreen.Services) },
            icon = { Icon(Icons.Default.CleaningServices, contentDescription = "Services") },
            label = { Text("Services") }
        )
        NavigationBarItem(
            modifier = Modifier.testTag("bottom_nav_book"),
            selected = currentScreen is AppScreen.Booking,
            onClick = { onNavigate(AppScreen.Booking()) },
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Book") },
            label = { Text("Book Now") }
        )
        NavigationBarItem(
            modifier = Modifier.testTag("bottom_nav_reviews"),
            selected = currentScreen is AppScreen.CustomerReviews,
            onClick = { onNavigate(AppScreen.CustomerReviews) },
            icon = { Icon(Icons.Default.Star, contentDescription = "Reviews") },
            label = { Text("Reviews") }
        )
        NavigationBarItem(
            modifier = Modifier.testTag("bottom_nav_contact"),
            selected = currentScreen is AppScreen.ContactUs || currentScreen is AppScreen.AboutUs,
            onClick = { onNavigate(AppScreen.ContactUs) },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Contact") },
            label = { Text("Contact") }
        )
    }
}

@Composable
fun RatingStars(
    rating: Int,
    maxRating: Int = 5,
    starSize: Int = 16,
    onRatingChanged: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxRating) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Default.Star else Icons.Default.StarBorder
            val tint = if (isSelected) SolarGoldSecondary else MaterialTheme.colorScheme.outline

            Icon(
                imageVector = icon,
                contentDescription = "Star $i",
                tint = tint,
                modifier = Modifier
                    .size(starSize.dp)
                    .then(
                        if (onRatingChanged != null) {
                            Modifier.clickable { onRatingChanged(i) }
                        } else Modifier
                    )
            )
            if (i < maxRating) {
                Spacer(modifier = Modifier.width(2.dp))
            }
        }
    }
}

@Composable
fun QuickContactButtonsRow(
    onCallClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onCallClick,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .testTag("quick_call_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = WaterBluePrimary
            ),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Call Now", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }

        Button(
            onClick = onWhatsAppClick,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .testTag("quick_whatsapp_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF25D366)
            ),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("WhatsApp", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }

        Button(
            onClick = onBookClick,
            modifier = Modifier
                .weight(1.1f)
                .height(48.dp)
                .testTag("quick_book_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SolarGoldSecondary
            ),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Book Service", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }
    }
}
