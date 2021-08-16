package com.easycruise.recipes_compose.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.easycruise.recipes_compose.R
import com.easycruise.recipes_compose.presentation.BaseApplication
import com.easycruise.recipes_compose.presentation.components.*
import com.easycruise.recipes_compose.ui.theme.RecipesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalComposeUiApi // LocalSoftwareKeyboardController needs this for now
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                RecipesTheme(
                    darkTheme = application.isDark.value
                ) {
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value

                    val keyboardController = LocalSoftwareKeyboardController.current
                    val focusManager = LocalFocusManager.current

                    val loading = viewModel.loading.value

                    val page = viewModel.page.value

                    val scaffoldState = rememberScaffoldState() //this will persist across recomposes

                    Scaffold(
                        topBar = {
                            SearchAppBar(  //state hoisting, moving states to separate composable (stateless composable, can't change state itself), also improves reusability
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        lifecycleScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Invalid category: MILK",
                                                actionLabel = "Hide"
                                            )
                                        }
                                    } else {
                                        viewModel.newSearch()  //we want snackbar to go away if fragment is destroyed therefore we use lifecycleScope
                                    }
                                },
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                keyboardController = keyboardController,
                                focusManager = focusManager,
                                onToggleTheme = {
                                    application.toggleTheme()
                                }
                            )
                        },
//                        bottomBar = {
//                            MyBottomBar()
//                        },
                        drawerContent = {
                            MyDrawer()
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
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
                                        viewModel.onChangeRecipeScrollPosition(index)
                                        if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                                            viewModel.nextPage()
                                        }
                                        RecipeCard(
                                            recipe = recipe,
                                            onClick = {
                                                findNavController().navigate(R.id.showRecipe)
                                            })
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(
                                isDisplayed = loading
                            )
                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }

                    // PulsingDemo()
                    //
                    //  val state = remember{ mutableStateOf(HeartAnimationDefinition.HeartButtonState.IDLE) }
                    //  AnimatedHeartButton(
                    //      buttonState = state,
                    //      onToggle = {
                    //          state.value = if (state.value == HeartAnimationDefinition.HeartButtonState.IDLE) HeartAnimationDefinition.HeartButtonState.ACTIVE else HeartAnimationDefinition.HeartButtonState.IDLE
                    //      }
                    //  )
                }
            }
        }
    }
}