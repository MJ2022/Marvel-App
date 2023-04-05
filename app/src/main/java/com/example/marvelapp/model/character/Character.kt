package com.example.marvelapp.model.character

import android.os.Parcelable
import com.example.marvelapp.model.common.CategoryList
import com.example.marvelapp.model.common.Thumbnail
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics: CategoryList?,
    val stories: CategoryList?,
    val events: CategoryList?,
    val series: CategoryList?,
) : Parcelable
