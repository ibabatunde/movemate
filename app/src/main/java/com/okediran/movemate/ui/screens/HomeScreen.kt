package com.okediran.movemate.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.okediran.movemate.R
import com.okediran.movemate.Screen
import com.okediran.movemate.ui.theme.MainColor
import com.okediran.movemate.ui.theme.MoveMateTheme
import com.okediran.movemate.ui.theme.SecondaryColor
import com.okediran.movemate.ui.theme.TextMainColor
import com.okediran.movemate.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderView(
            navController = navController,
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .background(MainColor)
        )

        TrackingView(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
        )

        VehicleView(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun HeaderView(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(40.dp),
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(R.drawable.passport),
                    contentDescription = "Passport",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp).rotate(315f),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Your location",
                        style = Typography.bodySmall.copy(color = Color.Gray)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        "Wertheimer, Illinois",
                        style = Typography.bodyLarge.copy(color = Color.White)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_down_arrow),
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Card(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_notification),
                        contentDescription = "Notification",
                        modifier = Modifier.size(25.dp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier
            .fillMaxHeight()
            .weight(0.2f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(0.5f)
                .clickable {
                    navController.navigate(Screen.Search.route)
                }
        ) {
            Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = MainColor,
                        modifier = Modifier.size(25.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "Enter the receipt number...",
                        style = Typography.bodyMedium.copy(color = Color.Gray),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_scan),
                        contentDescription = "Scan",
                        tint = SecondaryColor,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TrackingView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Tracking", style = Typography.bodyLarge.copy(color = TextMainColor))
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Shipment Number",
                            style = Typography.bodySmall.copy(color = Color.Gray)
                        )
                        Text(
                            "NEJ20089934122231",
                            style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.load_track),
                        contentDescription = "Location",
                        modifier = Modifier.size(64.dp)
                    )
                }

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Card(
                                modifier = Modifier.size(30.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = SecondaryColor.copy(alpha = 0.4f)
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_package),
                                    contentDescription = "Package",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    "Sender",
                                    style = Typography.bodySmall.copy(color = Color.Gray)
                                )
                                Text(
                                    "Atlanta, 5243",
                                    style = Typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }

                        Row {
                            Card(
                                modifier = Modifier.size(30.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Green.copy(alpha = 0.4f)
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_package),
                                    contentDescription = "Package",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    "Receiver",
                                    style = Typography.bodySmall.copy(color = Color.Gray)
                                )
                                Text(
                                    "Chicago, 2342",
                                    style = Typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "Time",
                                style = Typography.bodySmall.copy(color = Color.Gray)
                            )
                            Row(
                                modifier = Modifier.padding(top = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    modifier = Modifier.size(10.dp),
                                    colors = CardDefaults.cardColors(Color.Green)
                                ) { }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("2 days - 3 days", style = Typography.bodyMedium)
                            }
                        }

                        Column {
                            Text(
                                "Status",
                                style = Typography.bodySmall.copy(color = Color.Gray)
                            )
                            Text("Waiting to collect", style = Typography.bodyMedium)
                        }
                    }
                }

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "Add",
                        modifier = Modifier.size(25.dp),
                        tint = SecondaryColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Stop", style = Typography.bodyMedium.copy(color = SecondaryColor))
                }
            }
        }
    }
}

@Composable
fun VehicleView(modifier: Modifier = Modifier) {
    val vehicles = remember {
        listOf(
            Vehicle("Ocean freight", "International", R.drawable.shipper),
            Vehicle("Cargo freight", "Reliable", R.drawable.trucks),
            Vehicle("Air freight", "International", R.drawable.plane),
            Vehicle("Ocean freight", "International", R.drawable.shipper),
            Vehicle("Cargo freight", "Reliable", R.drawable.trucks),
            Vehicle("Air freight", "International", R.drawable.plane)
        )
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text("Available vehicles", style = Typography.bodyLarge.copy(color = TextMainColor))
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(vehicles) { index, vehicle ->
                var visible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(index * 100L)
                    visible = true
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = slideInHorizontally(
                        initialOffsetX = { it / 2 },
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .width(160.dp)
                            .height(200.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(vehicle.heading, style = Typography.bodyLarge)
                                Text(vehicle.subHeading, style = Typography.bodySmall)
                            }
                            Image(
                                painter = painterResource(vehicle.img),
                                contentDescription = "Vehicle",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Vehicle(val heading: String, val subHeading: String, val img: Int)

@Preview(showBackground = true)
@Composable
private fun DisplayHomeScreen() {
    MoveMateTheme {
        HomeScreen(navController = NavHostController(context = LocalContext.current))
    }
}