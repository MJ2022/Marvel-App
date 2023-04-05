package com.example.marvelapp.model.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryList(
    val available: Int,
    val collectionURI: String,
    val items: List<CategorySummary>,
    val returned: Int
): Parcelable