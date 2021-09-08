package com.easycruise.recipes_compose.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easycruise.recipes_compose.R
import com.easycruise.recipes_compose.domain.model.Recipe
import com.easycruise.recipes_compose.util.DEFAULT_RECIPE_IMAGE
import com.easycruise.recipes_compose.util.loadPicture

const val IMAGE_HEIGHT = 260

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun TestRecipe() {
    RecipeView(
        recipe = Recipe(
            id = 2,
            title = "Test dugackog titlea",
            publisher = "Borko Cvejic",
            rating =  20,
            ingredients = listOf("Prvi", "Drugi", "Treci"),
            featuredImage = "https://picsum.photos/300/300"
        )
    )
}

@ExperimentalAnimationApi
@Composable
fun RecipeView(
    recipe: Recipe
) {

    val showIngredients = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        recipe.featuredImage?.let { url ->
            val image = loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .height(IMAGE_HEIGHT.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h3
                    )
                    val rank = recipe.rating.toString()
                    Text(
                        text = rank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End),
                        style = MaterialTheme.typography.h5
                    )
                }

                Row {
                    recipe.publisher?.let { publisher ->
                        Text(
                            text = recipe.dateUpdated?.let { updated ->
                                stringResource(id = R.string.label_updated_publisher, updated, publisher)
                            } ?: kotlin.run {
                                stringResource(id = R.string.label_by_publisher, publisher)
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(bottom = 8.dp)
                                .align(CenterVertically),
                            style = MaterialTheme.typography.caption,
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.label_show_ingredients),
                        Modifier.clickable {
                            showIngredients.value = !showIngredients.value
                        },
                        textAlign = TextAlign.Center
                    )
                }

                recipe.ingredients?.let { ingredientsList ->
                    for (ingredient in ingredientsList) {
                        AnimatedVisibility(
                            visible = showIngredients.value,
                            enter = slideInHorizontally() + fadeIn(initialAlpha = 0.3f, animationSpec = tween(durationMillis = 750, easing = LinearOutSlowInEasing)),
                            exit = shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally, animationSpec = tween(durationMillis = 750, easing = LinearEasing))
                        ) {
                            Text(
                                text = ingredient,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}