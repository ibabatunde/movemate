package com.okediran.movemate.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.okediran.movemate.R
import com.okediran.movemate.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateScreen(navController: NavHostController, screen: Screen) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screen.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painterResource(id = R.drawable.ic_back_arrow), // Replace with your back arrow icon resource
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ){
        ScreenContent(screen = screen)
    }
}

@Composable
fun ScreenContent(screen: Screen) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "You are on the ${screen.title} screen",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}