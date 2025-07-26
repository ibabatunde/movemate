package com.okediran.movemate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.okediran.movemate.R
import com.okediran.movemate.Screen
import com.okediran.movemate.ui.theme.MainColor
import com.okediran.movemate.ui.theme.MoveMateTheme
import com.okediran.movemate.ui.theme.NearWhite
import com.okediran.movemate.ui.theme.PopGreen
import com.okediran.movemate.ui.theme.Typography
import com.okediran.movemate.utils.AnimatedButton
import kotlinx.coroutines.delay

@Composable
fun CalculateResultScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = Unit) {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            systemUiController.setStatusBarColor(
                color = MainColor,
                darkIcons = false
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(NearWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.movemate_logo),
                contentDescription = "Movemate logo",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.size(32.dp))

        Image(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_box),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(32.dp))

        Text(
            text = "Total Estimated Amount",
            style = Typography.titleMedium.copy(
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal
            )
        )

        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            CountUp(start = 1200, limit = 1460)
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "USD",
                style = Typography.titleMedium.copy(
                    color = PopGreen,
                    fontSize = 20.sp,
                )
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
            textAlign = TextAlign.Center,
            text = "This amount is estimated this will vary if you change your location or weight",
            style = Typography.bodySmall.copy(
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.size(24.dp))

        AnimatedButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Back to home"
        ) {
            navController.navigate(Screen.Home.route)
        }
    }
}
@Composable
fun CountUp(start: Int = 0, limit: Int) {
    var count by remember { mutableIntStateOf(start) }

    LaunchedEffect(count) {
        if (count < limit) {
            delay(10)
            count += 3
        }
    }

    Text(
        text = "$${count.coerceAtMost(limit)}",
        style = Typography.bodyMedium.copy(
            color = PopGreen,
            fontSize = 28.sp,
        )
    )
}
@Preview
@Composable
fun SummaryPagePreview() {
    MoveMateTheme {
        CalculateResultScreen(navController = NavHostController(LocalContext.current))
    }
}

