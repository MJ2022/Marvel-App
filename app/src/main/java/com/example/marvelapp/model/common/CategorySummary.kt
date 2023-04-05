package com.example.marvelapp.model.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategorySummary(
    val name: String,
    val resourceURI: String,
    val type: String?
): Parcelable
