package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
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
import com.example.ui.theme.WaterBluePrimary
import com.example.ui.viewmodel.CleanCareViewModel

@Composable
fun WaterTankDetailScreen(
    viewModel: CleanCareViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val steps = viewModel.getWaterTankSteps()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("water_tank_detail_screen"),
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
                    painter = painterResource(id = R.drawable.img_tank_clean),
                    contentDescription = "Water Tank Cleaning",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    color = WaterBluePrimary,
                    shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "HYGIENE CERTIFIED PROCESS",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }

        // Title and Intro
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Water Tank Cleaning Service",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Underground & Overhead Tank Deep Decontamination",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = WaterBluePrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Drinking water tanks accumulate toxic mud layers, bacterial biofilm, and algae over time. At I.S CleanCare, we don't just sweep—we use heavy submersible pumps, high-pressure washers, and medical-grade disinfectants to leave your tank 100% spotless and germ-free.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    )
                )
            }
        }

        // Key Pillars Cards
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Why Our Tank Cleaning is Superior",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))

                PillarItem(
                    title = "Complete Mud & Sludge Extraction",
                    desc = "Zero leftover silt. Heavy de-watering pumps extract sediments from floor depressions."
                )
                PillarItem(
                    title = "Motorized High-Pressure Scrubbing",
                    desc = "Scours wall pores, removing stubborn calcification, slime, and dark algae."
                )
                PillarItem(
                    title = "Food-Grade Disinfection",
                    desc = "Safe anti-microbial treatment kills Salmonella, E. Coli, and bacterial pathogens without leaving chemical odors."
                )
                PillarItem(
                    title = "Both Overhead & Underground Tanks",
                    desc = "Equipped for concrete underground sumps, plastic tanks, and rooftop overhead containers."
                )
            }
        }

        // 5-Step Process
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Our 5-Step Cleaning Process",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Systematic, hygiene-focused, and thorough",
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
                            .background(WaterBluePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${step.stepNumber}",
                            color = Color.White,
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
                    onClick = { viewModel.openBooking(preselectedService = "Water Tank Cleaning") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("detail_book_tank_cta"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WaterBluePrimary)
                ) {
                    Text(
                        text = "Book Water Tank Cleaning Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
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
                    Text("Call for Immediate Slot")
                }
            }
        }
    }
}

@Composable
fun PillarItem(title: String, desc: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = WaterBluePrimary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(
                    text = desc,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
