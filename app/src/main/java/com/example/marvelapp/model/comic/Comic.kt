package com.example.marvelapp.model.comic

import android.os.Parcelable
import com.example.marvelapp.model.common.CategoryList
import com.example.marvelapp.model.common.Thumbnail
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comic(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: Thumbnail,
    val characters: CategoryList?,
    val creators: CategoryList?,
    val events: CategoryList?,
    val pageCount: String,
    val stories: CategoryList?,
    val series: CategoryList?,
) : Parcelable