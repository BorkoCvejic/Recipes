package com.easycruise.recipes_compose.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easycruise.recipes_compose.domain.model.Recipe
import com.easycruise.recipes_compose.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel() {

    //standard way of getting data from response
//    private val _recipes: MutableLiveData<List<Recipe>> = MutableLiveData() //can be changed
//
//    val recipes: LiveData<List<Recipe>> get() = _recipes //read only, observe this in fragment


    //better way for compose
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf()) //can be changed, store the value AND in case I update value trigger recompose for all elements using this data.

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null) // chip selected, viewModel survives phone rotation

    val loading = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            loading.value = true

            resetSearchState()

//            delay(2000)

            recipes.value = searchRecipes()
            loading.value = false
        }
    }

    private suspend fun searchRecipes(): List<Recipe> {
        return repository.search(
            token = token,
            page = page.value,
            query = query.value
        )
    }

    fun nextPage() {
        viewModelScope.launch {
            //prevent duplicate events due to recompose happening too quickly
            if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                loading.value = true
                incrementPage()
                Log.d("RecipeListViewModel", "nextPage: triggered: ${page.value}")

                // just to show pagination, api is fast
                delay(1000)

                if (page.value > 1) {
                    val result = searchRecipes()
                    Log.d("RecipeListViewModel", "nextPage: $result")
                    appendRecipes(result)
                }
                loading.value = false
            }
        }
    }

    //Append new recipes to the current list of recipes
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value++
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

}