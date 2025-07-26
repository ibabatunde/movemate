package com.okediran.movemate.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.okediran.movemate.R
import com.okediran.movemate.ui.theme.CoolBlue
import com.okediran.movemate.ui.theme.DarkGray
import com.okediran.movemate.ui.theme.Frosty
import com.okediran.movemate.ui.theme.GlowGreen
import com.okediran.movemate.ui.theme.Charcoal
import com.okediran.movemate.ui.theme.IndicatorOrange
import com.okediran.movemate.ui.theme.Midnight
import com.okediran.movemate.ui.theme.RoyalPurple
import com.okediran.movemate.ui.theme.SlateGray

@Composable
fun Shipment(modifier: Modifier = Modifier, shipmentItem: ShipmentItem) {
    Card(
        modifier = modifier
            .background(Color.White)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            val (
                shipmentStatus,
                arrivalDay,
                deliveryDetails,
                cost,
                dot,
                date,
                packageIcon
            ) = createRefs()
            val statusText: String
            val statusColor: Color
            @DrawableRes val statusIcon: Int
            when (shipmentItem.shipmentStatus) {
                ShipmentStatus.IN_PROGRESS -> {
                    statusText = stringResource(R.string.in_progress)
                    statusColor = GlowGreen
                    statusIcon = R.drawable.ic_in_progress
                }

                ShipmentStatus.PENDING -> {
                    statusText = stringResource(R.string.pending)
                    statusColor = IndicatorOrange
                    statusIcon = R.drawable.ic_pending
                }

                ShipmentStatus.LOADING -> {
                    statusText = stringResource(R.string.loading)
                    statusColor = CoolBlue
                    statusIcon = R.drawable.ic_loading
                }

                ShipmentStatus.CANCELLED -> {
                    statusText = stringResource(R.string.cancelled)
                    statusColor = Color.Red
                    statusIcon = R.drawable.ic_cancelled
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Frosty, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .constrainAs(shipmentStatus) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.size(13.dp),
                    tint = statusColor,
                    imageVector = ImageVector.vectorResource(id = statusIcon),
                    contentDescription = statusText
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    modifier = Modifier,
                    text = statusText.lowercase(),
                    color = statusColor,
                    fontSize = 12.sp
                )
            }

            Text(
                modifier = Modifier.constrainAs(arrivalDay) {
                    top.linkTo(shipmentStatus.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                },
                text = stringResource(R.string.arriving_today),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Charcoal
                )
            )
            val deliveryDescription = stringResource(
                R.string.delivery_desc_template,
                shipmentItem.shipmentCode,
                shipmentItem.origin
            )
            Text(
                modifier = Modifier.constrainAs(deliveryDetails) {
                    top.linkTo(arrivalDay.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                },
                text = deliveryDescription,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = SlateGray
                )
            )

            Text(
                modifier = Modifier.constrainAs(cost) {
                    top.linkTo(deliveryDetails.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                },
                text = stringResource(R.string.shipment_cost_template, shipmentItem.cost),
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = RoyalPurple
                )
            )

            Text(
                modifier = Modifier.constrainAs(dot) {
                    top.linkTo(cost.top)
                    bottom.linkTo(cost.bottom)
                    start.linkTo(cost.end, margin = 4.dp)
                },
                text = stringResource(R.string.dot),
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkGray,
                )
            )

            Text(
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(cost.top)
                    bottom.linkTo(cost.bottom)
                    start.linkTo(dot.end, margin = 4.dp)
                },
                text = shipmentItem.date,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Midnight,
                )
            )

            Image(
                modifier = Modifier
                    .size(80.dp)
                    .constrainAs(packageIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(id = R.drawable.ic_delivery),
                contentDescription = stringResource(R.string.delivery)
            )
        }
    }
}

data class ShipmentItem(
    val shipmentStatus: ShipmentStatus,
    val shipmentCode: String,
    val origin: String,
    val cost: String,
    val date: String
)

enum class ShipmentStatus {
    IN_PROGRESS,
    PENDING,
    LOADING,
    CANCELLED
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF2A2B34)
fun ShipmentPreview() {
    val shipmentItem = ShipmentItem(
        shipmentStatus = ShipmentStatus.CANCELLED,
        shipmentCode = "NEJ20089934122231",
        origin = "Atlanta",
        cost = "$1400",
        date = "Sep 20, 2023"
    )
    Shipment(shipmentItem = shipmentItem)
}