package com.example.marvelapp.model.series

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: SeriesDataContainer? = null,
    val etag: String,
) : Parcelable
