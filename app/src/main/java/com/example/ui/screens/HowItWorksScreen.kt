package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SolarGoldSecondary
import com.example.ui.theme.WaterBluePrimary
import com.example.ui.viewmodel.CleanCareViewModel

@Composable
fun HowItWorksScreen(
    viewModel: CleanCareViewModel,
    modifier: Modifier = Modifier
) {
    val tankSteps = viewModel.getWaterTankSteps()
    val solarSteps = viewModel.getSolarWashSteps()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("how_it_works_screen"),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "How Our Service Works",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "From your initial booking to our final quality sign-off, here is our transparent and hassle-free service workflow.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    )
                }
            }
        }

        // 4 Main Customer Milestones
        item {
            Text(
                text = "Simple 4-Step Customer Journey",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            JourneyStepCard(
                step = "1",
                emoji = "📝",
                title = "Request Your Service",
                desc = "Choose Water Tank Cleaning, Solar Plate Wash, or the Combo Saver package. Enter your address in Faisal Town or nearby areas."
            )
        }
        item {
            JourneyStepCard(
                step = "2",
                emoji = "📞",
                title = "Instant Confirmation",
                desc = "We review your requested time slot, confirm via call/WhatsApp, and assign our trained technical team."
            )
        }
        item {
            JourneyStepCard(
                step = "3",
                emoji = "🧼",
                title = "Execution with Professional Equipment",
                desc = "Our team arrives on time equipped with de-watering pumps, high-pressure washers, food-grade disinfectants, and soft solar brushes."
            )
        }
        item {
            JourneyStepCard(
                step = "4",
                emoji = "✨",
                title = "Inspection & Clean Handover",
                desc = "You inspect the crystal clear tank and shining solar panels. We clean up all service waste and issue your completion receipt."
            )
        }

        // Water Tank Cleaning Steps
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "💧 Water Tank Detailed Procedure",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = WaterBluePrimary
                )
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    tankSteps.forEachIndexed { index, step ->
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "${step.stepNumber}. ", fontWeight = FontWeight.Bold, color = WaterBluePrimary)
                            Column {
                                Text(text = "${step.title} ${step.iconEmoji}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(text = step.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        if (index < tankSteps.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                        }
                    }
                }
            }
        }

        // Solar Wash Steps
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "☀️ Solar Plate Wash Detailed Procedure",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD97706)
                )
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    solarSteps.forEachIndexed { index, step ->
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "${step.stepNumber}. ", fontWeight = FontWeight.Bold, color = SolarGoldSecondary)
                            Column {
                                Text(text = "${step.title} ${step.iconEmoji}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(text = step.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        if (index < solarSteps.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                        }
                    }
                }
            }
        }

        // Bottom CTA
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { viewModel.openBooking() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WaterBluePrimary)
            ) {
                Text("Schedule Your Service Now", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun JourneyStepCard(
    step: String,
    emoji: String,
    title: String,
    desc: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(WaterBluePrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = step, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = emoji)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = desc, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            }
        }
    }
}
