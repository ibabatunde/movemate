package com.okediran.movemate.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateScreen(navController: NavHostController, screen: Screen) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(screen.title)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            ) {
                DestinationBox()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
            ) {
                PackagingBox()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                CategoriesBox(navController)
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesBox(navController: NavHostController) {
    val selectedCategories = remember { mutableStateListOf<String>() }
    val categories = listOf(
        "Document", "Glass", "Liquid", "Food", "Electronics", "Product", "Others"
    )

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.CalculateResult.route) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryColor,
                    )

                ) {
                    Text(
                        "Calculate",
                        style = Typography.bodyLarge.copy(color = Color.White),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 72.dp // Add space above button
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text("Categories", style = Typography.bodyLarge.copy(color = TextMainColor))
                Spacer(modifier = Modifier.height(4.dp))
                Text("What are you sending?", style = Typography.bodyMedium.copy(color = Color.Gray))
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { category ->
                        CategoryPill(
                            category = category,
                            isChecked = selectedCategories.contains(category)
                        ) { isChecked ->
                            if (isChecked) {
                                selectedCategories.add(category)
                            } else {
                                selectedCategories.remove(category)
                            }
                        }
                    }
                }
            }
        }
    }
}


private enum class PillState { Idle, Pressed }

@Composable
fun CategoryPill(
    category: String,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit
) {
    var pillState by remember { mutableStateOf(PillState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (pillState == PillState.Pressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "pill animation"
    )

    val backgroundColor: Color
    val borderColor: Color
    val textColor: Color
    if (isChecked) {
        backgroundColor = MainColor
        borderColor = Color.Transparent
        textColor = Color.White
    } else {
        backgroundColor = Color.White
        borderColor = MainColor
        textColor = TextMainColor
    }
    Row(
        modifier = Modifier
            .scale(scale)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .toggleable(
                value = isChecked,
                onValueChange = {
                    onCheckChange(!isChecked)
                    pillState = PillState.Pressed
                }
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isChecked) {
            Icon(
                tint = textColor,
                painter = painterResource(R.drawable.check),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = category,
            style = Typography.bodyMedium.copy(color = textColor)
        )
        LaunchedEffect(pillState) {
            if (pillState == PillState.Pressed) {
                delay(150)
                pillState = PillState.Idle
            }
        }
    }
}

@Composable
fun PackagingBox() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Packaging", style = Typography.bodyLarge.copy(color = TextMainColor))
        Spacer(modifier = Modifier.height(4.dp))
        Text("What are you sending?", style = Typography.bodyMedium.copy(color = Color.Gray))
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_box),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    VerticalDivider(
                        thickness = 0.5.dp,
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Box", style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold))

                }
                Icon(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(270f),
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun DestinationBox() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Destination", style = Typography.bodyLarge.copy(color = TextMainColor))
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                var senderLocation by remember { mutableStateOf("") }
                var receiverLocation by remember { mutableStateOf("") }
                var weight by remember { mutableStateOf("") }
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.box_arrow_up),
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        VerticalDivider(
                            thickness = 0.5.dp,
                            color = Color.Gray,
                            modifier = Modifier
                                .width(1.dp)
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = senderLocation,
                            onValueChange = { senderLocation = it },
                            placeholder = { Text("Sender location") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = MainColor,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(0.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.box_arrow_up),
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(180f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        VerticalDivider(
                            thickness = 0.5.dp,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .padding(8.dp)

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = receiverLocation,
                            onValueChange = { receiverLocation = it },
                            placeholder = { Text("Receiver location") },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .height(56.dp)
                                .heightIn(min = 60.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = MainColor,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(0.dp)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_weight),
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        VerticalDivider(
                            thickness = 0.5.dp,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .padding(8.dp)

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = weight,
                            onValueChange = { weight = it },
                            placeholder = { Text("Approx weight") },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .height(56.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = MainColor,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(0.dp)
                        )

                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CalculateScreenPrev() {
    MoveMateTheme {
        CalculateScreen(
            navController = NavHostController(LocalContext.current),
            screen = Screen.Calculate
        )

    }
}