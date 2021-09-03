package com.easycruise.recipes_compose.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PulsingDemo() {

    val color = SolidColor(MaterialTheme.colors.primary)

    val pulseState = rememberInfiniteTransition().animateFloat(
        initialValue = 30f,
        targetValue = 70f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 750,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            modifier = Modifier
                .align(CenterVertically)
                .height(pulseState.value.dp)
                .width(pulseState.value.dp)
        )

        Canvas(
            modifier = Modifier
                .height(55.dp)
        ) {
            drawCircle(
                radius = pulseState.value,
                brush = color
            )
        }
    }
}
