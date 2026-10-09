package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.components.CleanCareBottomNavigationBar
import com.example.ui.components.CleanCareTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SolarGoldSecondary
import com.example.ui.theme.WaterBluePrimary
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CleanCareViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CleanCareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CleanCareApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CleanCareApp(viewModel: CleanCareViewModel) {
    val context = LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsState()
    val bookings by viewModel.bookings.collectAsState()

    // Handle system back button for nested screens
    BackHandler(enabled = currentScreen !is AppScreen.Home) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CleanCareTopAppBar(
                currentScreen = currentScreen,
                onBackClick = { viewModel.navigateBack() },
                onCallClick = { viewModel.callBusiness(context) },
                onWhatsAppClick = { viewModel.openWhatsAppChat(context) },
                onBookingsClick = { viewModel.navigateTo(AppScreen.MyBookings) },
                bookingCount = bookings.size
            )
        },
        bottomBar = {
            CleanCareBottomNavigationBar(
                currentScreen = currentScreen,
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        },
        floatingActionButton = {
            if (currentScreen is AppScreen.Home || currentScreen is AppScreen.Services) {
                FloatingActionButton(
                    onClick = { viewModel.openBooking() },
                    containerColor = SolarGoldSecondary,
                    contentColor = Color.Black,
                    modifier = Modifier.testTag("fab_book_service")
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Book a Service"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetScreen ->
                when (targetScreen) {
                    is AppScreen.Home -> HomeScreen(viewModel = viewModel)
                    is AppScreen.Services -> ServicesScreen(viewModel = viewModel)
                    is AppScreen.WaterTankDetail -> WaterTankDetailScreen(viewModel = viewModel)
                    is AppScreen.SolarWashDetail -> SolarWashDetailScreen(viewModel = viewModel)
                    is AppScreen.Booking -> BookingScreen(viewModel = viewModel)
                    is AppScreen.MyBookings -> MyBookingsScreen(viewModel = viewModel)
                    is AppScreen.HowItWorks -> HowItWorksScreen(viewModel = viewModel)
                    is AppScreen.AboutUs -> AboutScreen(viewModel = viewModel)
                    is AppScreen.CustomerReviews -> ReviewsScreen(viewModel = viewModel)
                    is AppScreen.ContactUs -> ContactScreen(viewModel = viewModel)
                }
            }
        }
    }
}
