package com.example.marvelapp.model.series

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "marvel_series")
@Parcelize
data class SeriesInfoTable(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "comics")
    val comics: String,

    @ColumnInfo(name = "stories")
    val stories: String,

    @ColumnInfo(name = "events")
    val events: String,

    @ColumnInfo(name = "characters")
    val characters: String,

    @ColumnInfo(name = "creators")
    val creators: String,

    ) : Parcelable