package com.easycruise.recipes_compose.presentation.components

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.easycruise.recipes_compose.R
import com.easycruise.recipes_compose.domain.model.Recipe
import com.easycruise.recipes_compose.presentation.ui.recipe_list.PAGE_SIZE

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    nextPage: () -> Unit,
    navController: NavController
) {
    Box(  // overlays children, lower views will be on top
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && recipes.isEmpty()) {
            Log.d("RecipeListFragment", "loading") // add shimmer effect
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        nextPage()
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            val bundle = Bundle()
                            recipe.id?.let { id ->
                                bundle.putInt("recipeId", id)
                            }
                            navController.navigate(R.id.showRecipe, bundle)
                        })
                }
            }
        }
    }
}