package com.easycruise.recipes_compose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easycruise.recipes_compose.domain.model.Recipe
import com.easycruise.recipes_compose.util.DEFAULT_RECIPE_IMAGE
import com.easycruise.recipes_compose.util.loadPicture

const val IMAGE_HEIGHT = 260

@Preview(showBackground = true)
@Composable
fun TestRecipe() {
    RecipeView(
        recipe = Recipe(
            2, "Test dugackog titlea", "Borko Cvejic", null, 20, null, null, null,
            listOf("Prvi", "Drugi", "Treci"), null, null, null, null
        )
    )
}

@Composable
fun RecipeView(
    recipe: Recipe
) {
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
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h5
                    )

                }

                recipe.publisher?.let { publisher ->
                    Text(
                        text = recipe.dateUpdated?.let { updated ->
                            "Updated $updated by $publisher "
                        } ?: kotlin.run {
                            "By $publisher"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.caption
                    )
                }

                recipe.ingredients?.let { ingredientsList ->
                    for (ingredient in ingredientsList) {
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