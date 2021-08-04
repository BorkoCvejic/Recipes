package com.easycruise.recipes_compose.di

import com.easycruise.recipes_compose.network.RecipeService
import com.easycruise.recipes_compose.network.model.RecipeDtoMapper
import com.easycruise.recipes_compose.repository.RecipeRepository
import com.easycruise.recipes_compose.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepository_Impl( recipeService, recipeDtoMapper)
    }
}