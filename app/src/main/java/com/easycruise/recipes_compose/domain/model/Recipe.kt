package com.easycruise.recipes_compose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int? = null,
    val title: String? = null,
    val publisher: String? = null,
    val featuredImage: String? = null,
    val rating: Int? = 0,
    val sourceUrl: String? = null,
    val description: String? = null,
    val cookingInstructions: String? = null,
    val ingredients: List<String>? = listOf(),
    val dateAdded: String? = null,
    val dateUpdated: String? = null,
    val longDateAdded: Long? = null,
    val longDateUpdated: Long? = null
) : Parcelable