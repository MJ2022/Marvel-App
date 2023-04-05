package com.example.marvelapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvelapp.model.comic.ComicInfoTable

@Dao
interface ComicInfoDAO {
    @Query("select * from marvel_comics order by title")
    fun getAllComics(): LiveData<List<ComicInfoTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ComicInfoTable)
}