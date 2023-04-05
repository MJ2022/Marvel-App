package com.example.marvelapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvelapp.model.series.SeriesInfoTable

@Dao
interface SeriesInfoDAO {
    @Query("select * from marvel_series order by title")
    fun getAllSeries(): LiveData<List<SeriesInfoTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SeriesInfoTable)
}