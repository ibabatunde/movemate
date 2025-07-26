package com.okediran.movemate.utils

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okediran.movemate.R
import com.okediran.movemate.ui.theme.SecondaryColor
import kotlinx.coroutines.delay

@Composable
fun AnimatedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Pressed) 0.90f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "Button animation"
    )

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = SecondaryColor,
            contentColor = Color.White
        ),
        onClick = {
            buttonState = ButtonState.Pressed
            onClick()
        },
        modifier = modifier
            .scale(scale)
            .height(54.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 17.sp
        )
    }

    LaunchedEffect(buttonState) {
        if (buttonState == ButtonState.Pressed) {
            delay(100)
            buttonState = ButtonState.Idle
        }
    }
}

@Preview
@Composable
fun AnimatedButtonPreview() {
    AnimatedButton(
        text = "Calculate",
        onClick = {}
    )
}

private enum class ButtonState { Idle, Pressed }