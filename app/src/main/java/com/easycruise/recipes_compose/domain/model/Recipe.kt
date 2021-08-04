package com.easycruise.recipes_compose.domain.model

import android.os.Parcel
import android.os.Parcelable

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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}