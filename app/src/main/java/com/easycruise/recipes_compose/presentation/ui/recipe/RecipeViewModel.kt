package com.easycruise.recipes_compose.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easycruise.recipes_compose.domain.model.Recipe
import com.easycruise.recipes_compose.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.easycruise.recipes_compose.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("RECIPE_VIEW_MODEL", e.cause.toString())
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true

        val recipe = recipeRepository.get(token = token, id = id)
        this.recipe.value = recipe

        loading.value = false
     }


}