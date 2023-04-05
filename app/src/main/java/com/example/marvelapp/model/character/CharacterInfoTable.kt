package com.example.marvelapp.model.character

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "marvel_characters")
@Parcelize
data class CharacterInfoTable(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
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

    @ColumnInfo(name = "series")
    val series: String,

    ) : Parcelable
