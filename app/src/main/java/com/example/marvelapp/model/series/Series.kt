package com.example.marvelapp.model.series

import android.os.Parcelable
import com.example.marvelapp.model.common.CategoryList
import com.example.marvelapp.model.common.Thumbnail

import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics: CategoryList?,
    val stories: CategoryList?,
    val events: CategoryList?,
    val characters: CategoryList?,
    val creators: CategoryList?,
) : Parcelable
