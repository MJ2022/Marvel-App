package com.example.marvelapp.model.comic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: ComicDataContainer? = null,
    val etag: String,
) : Parcelable
