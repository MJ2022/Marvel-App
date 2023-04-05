package com.example.marvelapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marvelapp.model.character.CharacterInfoTable
import com.example.marvelapp.model.comic.ComicInfoTable
import com.example.marvelapp.model.series.SeriesInfoTable

@Database(
    entities = [CharacterInfoTable::class, ComicInfoTable::class, SeriesInfoTable::class],
    version = 3,
    exportSchema = false
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterInfoDAO
    abstract fun comicDao(): ComicInfoDAO
    abstract fun seriesDao(): SeriesInfoDAO

    companion object {
        private var INSTANCE: MarvelDatabase? = null
        fun getInstance(context: Context): MarvelDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MarvelDatabase::class.java,
                        "marvel_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}