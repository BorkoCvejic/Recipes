package com.easycruise.recipes_compose.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easycruise.recipes_compose.domain.model.Recipe
import com.easycruise.recipes_compose.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String
): ViewModel() {

    //standard way of getting data from response
//    private val _recipes: MutableLiveData<List<Recipe>> = MutableLiveData() //can be changed
//
//    val recipes: LiveData<List<Recipe>> get() = _recipes //read only, observe this in fragment


    //better way for compose
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf()) //can be changed


    init {
        newSearch()
    }

    private fun newSearch() {
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                page = 1,
                query = "chicken"
            )
            recipes.value = result

        }
    }
}