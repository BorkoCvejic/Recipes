package com.easycruise.recipes_compose.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.easycruise.recipes_compose.presentation.BaseApplication
import com.easycruise.recipes_compose.presentation.components.RecipeView
import com.easycruise.recipes_compose.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.easycruise.recipes_compose.ui.theme.RecipesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.getInt("recipeId")?.let { rId ->
            viewModel.onTriggerEvent(GetRecipeEvent(rId))
        }

        return ComposeView(requireContext()).apply {
            setContent {

                val loading = viewModel.loading.value

                val recipe = viewModel.recipe.value

                val scaffoldState = rememberScaffoldState()

                RecipesTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            if (loading && recipe == null) {
                                Text(text = "Loading")
                            } else {
                                recipe?.let { recipe ->
                                    if (recipe.id == 1) {
                                        lifecycleScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "An error occured with this recipe.",
                                                actionLabel = "OK",
                                                duration = SnackbarDuration.Short
                                            )
                                            activity?.onBackPressed()
                                        }
                                    } else {
                                        RecipeView(recipe = recipe)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}