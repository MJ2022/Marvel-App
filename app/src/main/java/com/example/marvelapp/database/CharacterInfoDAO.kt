package com.example.marvelapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvelapp.model.character.CharacterInfoTable

@Dao
interface CharacterInfoDAO {
    @Query("select * from marvel_characters order by name")
    fun getAllCharacters(): LiveData<List<CharacterInfoTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CharacterInfoTable)
}