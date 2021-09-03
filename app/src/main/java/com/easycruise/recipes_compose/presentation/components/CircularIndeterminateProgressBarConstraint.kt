package com.easycruise.recipes_compose.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun CircularIndeterminateProgressBarConstraint(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (progressBar, text) = createRefs()
            val topGuideline = createGuidelineFromTop(0.3f) // guideline which is 30% from top of the parent

            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
//                    top.linkTo(parent.top)
//                    bottom.linkTo(parent.bottom)
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = MaterialTheme.colors.primary
            )

            Text(
                text = "Loading...",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp
                ),
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(progressBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Preview
@Composable
fun CircularProgressBarConstraintPreview() {
    CircularIndeterminateProgressBarConstraint(
        isDisplayed = true
    )
}