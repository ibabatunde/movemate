package com.okediran.movemate

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.okediran.movemate.navigation.BottomNavigationBar
import com.okediran.movemate.ui.screens.CalculateScreen
import com.okediran.movemate.ui.screens.HomeScreen
import com.okediran.movemate.ui.screens.ProfileScreen
import com.okediran.movemate.ui.screens.ShippingScreen


sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Calculate : Screen("calculate", "Calculate",  R.drawable.ic_calculate)
    object Shipment : Screen("shipment", "Shipment",  R.drawable.ic_history)
    object Profile : Screen("profile", "Profile", R.drawable.ic_person)
}
@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Calculate.route) {
                CalculateScreen(navController = navController, screen = Screen.Calculate)
            }
            composable(Screen.Shipment.route) {
                ShippingScreen(navController = navController, screen = Screen.Shipment)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController, screen = Screen.Profile)
            }
        }
    }
}