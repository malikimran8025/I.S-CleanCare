package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.SolarGoldSecondary
import com.example.ui.viewmodel.CleanCareViewModel

@Composable
fun SolarWashDetailScreen(
    viewModel: CleanCareViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val steps = viewModel.getSolarWashSteps()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("solar_wash_detail_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Banner Image
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_solar_clean),
                    contentDescription = "Solar Plate Wash",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    color = SolarGoldSecondary,
                    shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "MAXIMUM SOLAR YIELD",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            }
        }

        // Title and Intro
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Solar Plate Wash Service",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Professional Panel Wash & Solar Output Optimization",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFFD97706),
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "In urban Lahore areas like Faisal Town, air pollution, road dust, and bird droppings quickly create a light-blocking film over photovoltaic glass. This causes up to a 30% drop in your daily solar production! Our specialized soft washing restores full kilowatt capacity without damaging the sensitive anti-reflective coating.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    )
                )
            }
        }

        // Efficiency Stat Highlight
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFEF3C7)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(SolarGoldSecondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Bolt, contentDescription = null, tint = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Up to 28% More Electricity",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF78350F),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Clean panels absorb significantly more sunlight, lowering your electricity bills immediately.",
                            fontSize = 12.sp,
                            color = Color(0xFF92400E)
                        )
                    }
                }
            }
        }

        // Key Features
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Safe & Professional Handling",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))

                PillarItem(
                    title = "Non-Abrasive Soft Microfiber",
                    desc = "Zero scratch brushes prevent micro-abrasions that permanently degrade panel life."
                )
                PillarItem(
                    title = "Demineralized Water Rinse",
                    desc = "Leaves zero hard water white spots or mineral stains on the tempered glass."
                )
                PillarItem(
                    title = "Safe Rooftop Equipment",
                    desc = "Telescopic extension water poles allow thorough cleaning of high or steep rooftops safely."
                )
                PillarItem(
                    title = "Baked Dirt & Dropping Treatment",
                    desc = "Specialized eco-safe washing agents soften baked bird poop without harsh chemicals."
                )
            }
        }

        // Steps
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "How We Wash Your Solar Panels",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Gentle care for maximum energy yield",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(steps) { step ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SolarGoldSecondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${step.stepNumber}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = step.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = step.iconEmoji)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = step.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }
        }

        // Action CTAs
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { viewModel.openBooking(preselectedService = "Solar Plate Wash") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("detail_book_solar_cta"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SolarGoldSecondary)
                ) {
                    Text(
                        text = "Book Solar Plate Wash Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = { viewModel.callBusiness(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Call for Quick Estimate")
                }
            }
        }
    }
}
