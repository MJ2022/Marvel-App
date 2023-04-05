package com.example.marvelapp.model.comic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicDataContainer(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Comic>,
    val total: Int
) : Parcelable
