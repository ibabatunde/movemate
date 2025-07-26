package com.okediran.movemate.ui.screens

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.okediran.movemate.R
import com.okediran.movemate.ui.theme.MainColor
import com.okediran.movemate.ui.theme.MoveMateTheme
import com.okediran.movemate.ui.theme.SecondaryColor
import com.okediran.movemate.ui.theme.Typography

@Composable
fun SearchScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(MainColor)
        ) {
            SearchHeader(navController)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(5f)
        ){
            ListBody()
        }
    }
}

@Composable
fun ListBody() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            itemsIndexed(
                listOf(
                    Orders("Summer linen Jacket", "#NEJ20089934122231 · Paris → London"),
                    Orders("Tappered-fit jeans AW", "#NEJ20089934122236 · Lagos → Dubai"),
                    Orders("Macbook pro M2", "#NEJ20089934122238 · Atlanta → Florida"),
                    Orders("Office setup desk", "#NEJ20089934122266 · France → Germany")
                )
            ) {
                index, order ->
                Row {
                    Card(
                        modifier = Modifier
                            .size(32.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MainColor
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_box),
                            contentDescription = "Package",
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(order.title, style = Typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(order.description, style = Typography.bodySmall.copy(color = Color.Gray))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (index != 3) {
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 0.5.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SearchHeader(navController: NavHostController) {
    var search by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back_arrow),
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    navController.navigateUp()
                },
            tint = Color.White
        )
        Card(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.5f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = MainColor,
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = { Text("Receipt no...") },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .heightIn(min = 60.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = TextFieldDefaults.colors(
                        cursorColor = MainColor,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedPlaceholderColor = Color.LightGray,
                        unfocusedLabelColor = Color.DarkGray,
                        focusedLabelColor = Color.DarkGray,
                        unfocusedIndicatorColor = MainColor,
                        focusedIndicatorColor = MainColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
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
data class Orders(
    val title: String,
    val description: String
)
@Preview(showBackground = true)
@Composable
private fun SearchScreenPrev() {
    MoveMateTheme {
        SearchScreen(
            navController = NavHostController(LocalContext.current)
        )
    }
}