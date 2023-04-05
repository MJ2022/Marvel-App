package com.example.marvelapp.repository

import androidx.lifecycle.LiveData
import com.example.marvelapp.database.CharacterInfoDAO
import com.example.marvelapp.database.ComicInfoDAO
import com.example.marvelapp.database.SeriesInfoDAO
import com.example.marvelapp.model.character.CharacterInfoTable
import com.example.marvelapp.model.comic.ComicInfoTable
import com.example.marvelapp.model.series.SeriesInfoTable

class MarvelRepository(
    private val characterInfoDAO: CharacterInfoDAO,
    private val comicInfoDAO: ComicInfoDAO,
    private val seriesInfoDAO: SeriesInfoDAO,
) {
    //variable used to store all Characters data
    var readAllCharacterData: LiveData<List<CharacterInfoTable>> =
        characterInfoDAO.getAllCharacters()

    suspend fun addCharacter(item: CharacterInfoTable) {
        characterInfoDAO.insert(item)
    }

    //variable used to store all Comics data
    var readAllComicData: LiveData<List<ComicInfoTable>> =
        comicInfoDAO.getAllComics()

    suspend fun addComic(item: ComicInfoTable) {
        comicInfoDAO.insert(item)
    }

    //variable used to store all Series data
    var readAllSeriesData: LiveData<List<SeriesInfoTable>> =
        seriesInfoDAO.getAllSeries()

    suspend fun addSeries(item: SeriesInfoTable) {
        seriesInfoDAO.insert(item)
    }
}