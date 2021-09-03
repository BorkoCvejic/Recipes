package com.easycruise.recipes_compose.presentation.components

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.easycruise.recipes_compose.R
import com.easycruise.recipes_compose.presentation.components.HeartAnimationDefinition.HeartButtonState.ACTIVE
import com.easycruise.recipes_compose.presentation.components.HeartAnimationDefinition.HeartButtonState.IDLE

@Composable
fun AnimatedHeartButton(
    buttonState: MutableState<HeartAnimationDefinition.HeartButtonState>,
    onToggle: () -> Unit
) {

    val size by animateDpAsState(
        targetValue = if (buttonState.value == ACTIVE) 50.1.dp else 50.dp,
        animationSpec = keyframes {
            durationMillis = 750
            HeartAnimationDefinition.expandableIconSize.at(100)
            HeartAnimationDefinition.idleIconSize.at(200)
        },
        finishedListener = {
            Log.e("DEBUG", "TEST ANIMATION")
        }
    )

    HeartButton(
        buttonState = buttonState,
        onToggle = onToggle,
        size = size
    )
}

@Composable
private fun HeartButton (
    buttonState: MutableState<HeartAnimationDefinition.HeartButtonState>,
    onToggle: () -> Unit,
    size: Dp
) {
    when (buttonState.value) {
        ACTIVE -> {
            Image(
                painterResource(id = R.drawable.ic_heart_red),
                contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = onToggle)
                    .size(size)
            )
        }
        IDLE -> {
            Image(
                painterResource(id = R.drawable.ic_heart_grey),
                contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = onToggle)
                    .size(size)
            )
        }
    }
}

object HeartAnimationDefinition {

    enum class HeartButtonState {
        IDLE, ACTIVE
    }

    val idleIconSize = 50.dp
    val expandableIconSize = 80.dp
}

@Preview(showBackground = true)
@Composable
fun AnimatedHeartButtonPreview() {
    val state = remember{ mutableStateOf(IDLE) }
    AnimatedHeartButton(
        buttonState = state,
        onToggle = { state.value = if (state.value == IDLE) ACTIVE else IDLE}
    )
}
