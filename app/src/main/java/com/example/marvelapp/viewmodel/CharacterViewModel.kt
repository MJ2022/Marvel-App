package com.example.marvelapp.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.database.CharacterInfoDAO
import com.example.marvelapp.database.ComicInfoDAO
import com.example.marvelapp.database.MarvelDatabase
import com.example.marvelapp.database.SeriesInfoDAO
import com.example.marvelapp.model.character.*
import com.example.marvelapp.model.common.CategoryList
import com.example.marvelapp.model.common.CategorySummary
import com.example.marvelapp.model.common.Thumbnail
import com.example.marvelapp.network.MarvelAPI
import com.example.marvelapp.repository.MarvelRepository
import kotlinx.coroutines.launch

class CharacterViewModel: ViewModel() {

    var characterListResponse: CharacterDataWrapper by mutableStateOf(
        CharacterDataWrapper(
            0,
            "",
            "",
            "",
            "",
            null,
            ""
        )
    )

    //Room database variables
    //Character list
    lateinit var readAllCharacterData: LiveData<List<CharacterInfoTable>>

    //repository to access all database functionality
    private lateinit var repository: MarvelRepository

    //DAO
    private lateinit var characterDAO: CharacterInfoDAO
    private lateinit var comicDAO: ComicInfoDAO
    private lateinit var seriesDAO: SeriesInfoDAO
    private var errorMessage: String by mutableStateOf("")

    //initialize all data using context from activity
    fun initData(context: Context) {
        characterDAO = MarvelDatabase.getInstance(context).characterDao()
        comicDAO = MarvelDatabase.getInstance(context).comicDao()
        seriesDAO = MarvelDatabase.getInstance(context).seriesDao()
        repository = MarvelRepository(characterDAO, comicDAO, seriesDAO)
        readAllCharacterData = repository.readAllCharacterData
    }

    //insert character to database
    private fun insertCharacterIntoDatabase(character: CharacterInfoTable) {
        viewModelScope.launch {
            repository.addCharacter(character)
        }
    }

    //fetch character list from API
    fun getCharacterListFromAPI() {
        viewModelScope.launch {
            val marvelAPI = MarvelAPI.getInstance()
            try {
                val characterList = marvelAPI.getCharacters(ts = "tsor")
                processCharacterDataFromAPItoDatabase(characterList)
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    //function to store API Data to database
    private fun processCharacterDataFromAPItoDatabase(characterList: CharacterDataWrapper) {
        characterListResponse = characterList
        characterList.data!!.results.forEach {
            val comics = parseItemData(it.comics!!.items)
            val events = parseItemData(it.events!!.items)
            val series = parseItemData(it.series!!.items)
            val stories = parseItemData(it.stories!!.items)
            val thumbnail = it.thumbnail.path + "$$" + it.thumbnail.extension

            val characterRoomInfo = CharacterInfoTable(
                name = it.name,
                description = it.description,
                id = it.id,
                thumbnail = thumbnail,
                comics = comics,
                events = events,
                series = series,
                stories = stories
            )
            insertCharacterIntoDatabase(characterRoomInfo)
        }
    }

    //function to transform the API data for storage in the database
    private fun parseItemData(itemList: List<CategorySummary>): String {
        var categoryItem = ""
        itemList.forEach { item -> categoryItem += "${item.name}$$" }
        return categoryItem
    }

    //get data from database and save to Live state
    fun getLocalDatabaseData() {
        viewModelScope.launch {
            if (readAllCharacterData.value != null) {
                val character = mutableListOf<Character>()
                loadAllCharactersToList(readAllCharacterData.value!!, character)
                val tmpCharacterDataContainer = CharacterDataContainer(0, 10, 0, character, 10)
                val tmpCharacterDataWrapper =
                    CharacterDataWrapper(0, "", "", "", "", tmpCharacterDataContainer, "")
                characterListResponse = tmpCharacterDataWrapper
            }
        }
    }

    private fun loadAllCharactersToList(
        value: List<CharacterInfoTable>,
        character: MutableList<Character>
    ) {
        lateinit var comicItemList: MutableList<CategorySummary>
        lateinit var eventsItemList: MutableList<CategorySummary>
        lateinit var seriesItemList: MutableList<CategorySummary>
        lateinit var storiesItemList: MutableList<CategorySummary>

        value.forEach {
            comicItemList = mutableListOf()
            eventsItemList = mutableListOf()
            seriesItemList = mutableListOf()
            storiesItemList = mutableListOf()
            splitCategoryData(it.comics, comicItemList)
            splitCategoryData(it.events, eventsItemList)
            splitCategoryData(it.series, seriesItemList)
            splitCategoryData(it.stories, storiesItemList)

            character.add(
                Character(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    thumbnail = Thumbnail(
                        path = it.thumbnail.split("$$")[0],
                        extension = it.thumbnail.split("$$")[1]
                    ),
                    comics = CategoryList(0, "", items = comicItemList, 0),
                    events = CategoryList(0, "", items = eventsItemList, 0),
                    series = CategoryList(0, "", items = seriesItemList, 0),
                    stories = CategoryList(0, "", items = storiesItemList, 0)
                )
            )
        }
    }

    private fun splitCategoryData(item: String, itemList: MutableList<CategorySummary>) {
        item.split("$$").forEach { itemList.add(CategorySummary(name = it, "", "")) }
        itemList.removeLast()
    }
}