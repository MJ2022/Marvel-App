package com.example.marvelapp.model.series

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesDataContainer(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Series>,
    val total: Int
) : Parcelable
