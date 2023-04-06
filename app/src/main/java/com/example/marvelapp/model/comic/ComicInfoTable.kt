package com.example.marvelapp.model.comic

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "marvel_comics")
@Parcelize
data class ComicInfoTable(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "pageCount")
    val pageCount: String,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "characters")
    val characters: String,

    @ColumnInfo(name = "creators")
    val creators: String,

    @ColumnInfo(name = "events")
    val events: String,

    @ColumnInfo(name = "series")
    val series: String,

    @ColumnInfo(name = "stories")
    val stories: String,


    ) : Parcelable

