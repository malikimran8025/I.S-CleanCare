package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BusinessConfig
import com.example.ui.components.QuickContactButtonsRow
import com.example.ui.theme.SolarGoldSecondary
import com.example.ui.theme.WaterBlueContainer
import com.example.ui.theme.WaterBluePrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CleanCareViewModel

@Composable
fun ContactScreen(
    viewModel: CleanCareViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("contact_screen"),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Business Profile Header Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = WaterBlueContainer
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Surface(
                        color = WaterBluePrimary,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "OFFICIAL SERVICE HUB",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = BusinessConfig.BUSINESS_NAME,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0369A1)
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = WaterBluePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = BusinessConfig.LOCATION_LINE,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Text(
                        text = BusinessConfig.FULL_ADDRESS,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }
        }

        // Direct Action Buttons (Call Now | WhatsApp | Book a Service)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Instant Contact Options",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    QuickContactButtonsRow(
                        onCallClick = { viewModel.callBusiness(context) },
                        onWhatsAppClick = { viewModel.openWhatsAppChat(context) },
                        onBookClick = { viewModel.openBooking() }
                    )
                }
            }
        }

        // Contact Information Details
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
                    Text(
                        text = "Operating & Hub Details",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    ContactDetailRow(
                        icon = Icons.Default.Phone,
                        title = "Direct Helpline",
                        value = BusinessConfig.DISPLAY_PHONE,
                        actionLabel = "Call",
                        onClick = { viewModel.callBusiness(context) }
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

                    ContactDetailRow(
                        icon = Icons.Default.Chat,
                        title = "WhatsApp Chat",
                        value = BusinessConfig.DISPLAY_PHONE,
                        actionLabel = "Chat",
                        tint = Color(0xFF25D366),
                        onClick = { viewModel.openWhatsAppChat(context) }
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

                    ContactDetailRow(
                        icon = Icons.Default.Schedule,
                        title = "Working Hours",
                        value = BusinessConfig.WORKING_HOURS,
                        actionLabel = null,
                        onClick = null
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

                    ContactDetailRow(
                        icon = Icons.Default.Map,
                        title = "Map & Navigation",
                        value = "Faisal Town, B Block, Lahore",
                        actionLabel = "Directions",
                        onClick = { viewModel.openMapLocation(context) }
                    )
                }
            }
        }

        // Service Coverage Area
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.HomeWork,
                            contentDescription = null,
                            tint = WaterBluePrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Service Coverage Areas",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Our mobile cleaning vans are deployed across:",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    val areas = listOf(
                        "Faisal Town (Blocks A, B, C)",
                        "Model Town (All Blocks)",
                        "Johar Town (Phase 1 & 2)",
                        "Garden Town",
                        "Township",
                        "Gulberg III & Surroundings"
                    )

                    areas.chunked(2).forEach { pair ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            pair.forEach { area ->
                                Surface(
                                    modifier = Modifier.weight(1f),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "📍 $area",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                                    )
                                }
                            }
                            if (pair.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }

        // Navigation shortcuts to About Us, How It Works, and Share App
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    ListItem(
                        headlineContent = { Text("About I.S CleanCare", fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Our story, values, and certifications") },
                        leadingContent = { Icon(Icons.Default.Info, contentDescription = null, tint = WaterBluePrimary) },
                        trailingContent = { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) },
                        modifier = Modifier.clickable { viewModel.navigateTo(AppScreen.AboutUs) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    ListItem(
                        headlineContent = { Text("How Our Service Works", fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Step-by-step cleaning and safety protocol") },
                        leadingContent = { Icon(Icons.Default.Engineering, contentDescription = null, tint = SolarGoldSecondary) },
                        trailingContent = { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) },
                        modifier = Modifier.clickable { viewModel.navigateTo(AppScreen.HowItWorks) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    ListItem(
                        headlineContent = { Text("Share App with Neighbors", fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Recommend CleanCare to friends in Faisal Town") },
                        leadingContent = { Icon(Icons.Default.Share, contentDescription = null, tint = WaterBluePrimary) },
                        trailingContent = { Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) },
                        modifier = Modifier.clickable { viewModel.shareApp(context) }
                    )
                }
            }
        }
    }
}

@Composable
fun ContactDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    actionLabel: String?,
    tint: Color = WaterBluePrimary,
    onClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }

        if (actionLabel != null && onClick != null) {
            FilledTonalButton(
                onClick = onClick,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(actionLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
