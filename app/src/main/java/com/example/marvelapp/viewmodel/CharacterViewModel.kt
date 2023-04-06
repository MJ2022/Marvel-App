package com.example.marvelapp.viewmodel

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.database.CharacterInfoDAO
import com.example.marvelapp.database.ComicInfoDAO
import com.example.marvelapp.database.MarvelDatabase
import com.example.marvelapp.database.SeriesInfoDAO
import com.example.marvelapp.model.character.*
import com.example.marvelapp.model.common.CategoryList
import com.example.marvelapp.model.common.Thumbnail
import com.example.marvelapp.network.MarvelAPI
import com.example.marvelapp.repository.MarvelRepository
import com.example.marvelapp.util.Common
import com.example.marvelapp.view.CharacterItem
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    val common = Common()
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
    lateinit var readAllCharacterData: LiveData<List<CharacterInfoTable>>
    private lateinit var repository: MarvelRepository
    private lateinit var characterDAO: CharacterInfoDAO
    private lateinit var comicDAO: ComicInfoDAO
    private lateinit var seriesDAO: SeriesInfoDAO
    private var errorMessage: String by mutableStateOf("")

    fun initData(context: Context) {
        characterDAO = MarvelDatabase.getInstance(context).characterDao()
        comicDAO = MarvelDatabase.getInstance(context).comicDao()
        seriesDAO = MarvelDatabase.getInstance(context).seriesDao()
        repository = MarvelRepository(characterDAO, comicDAO, seriesDAO)
        readAllCharacterData = repository.readAllCharacterData
    }

    private fun insertCharacterIntoDatabase(character: CharacterInfoTable) {
        viewModelScope.launch {
            repository.addCharacter(character)
        }
    }

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

    private fun processCharacterDataFromAPItoDatabase(characterList: CharacterDataWrapper) {
        characterListResponse = characterList
        characterList.data!!.results.forEach {
            val characterRoomInfo = CharacterInfoTable(
                name = it.name,
                description = it.description,
                id = it.id,
                thumbnail = it.thumbnail.path + "$$" + it.thumbnail.extension,
                comics = common.parseItemData(it.comics!!.items),
                events = common.parseItemData(it.events!!.items),
                series = common.parseItemData(it.series!!.items),
                stories = common.parseItemData(it.stories!!.items)
            )
            insertCharacterIntoDatabase(characterRoomInfo)
        }
    }

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
        value.forEach {
            character.add(
                Character(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    thumbnail = Thumbnail(
                        path = it.thumbnail.split("$$")[0],
                        extension = it.thumbnail.split("$$")[1]
                    ),
                    comics = CategoryList(0, "", items = common.splitCategoryData(it.comics), 0),
                    events = CategoryList(0, "", items = common.splitCategoryData(it.events), 0),
                    series = CategoryList(0, "", items = common.splitCategoryData(it.series), 0),
                    stories = CategoryList(0, "", items = common.splitCategoryData(it.stories), 0)
                )
            )
        }
    }

    @Composable
    fun CharacterList(
        characterList: CharacterDataWrapper,
    ) {
        LazyColumn {
            characterList.data?.let { it ->
                itemsIndexed(items = it.results) { _, item ->
                    CharacterItem(
                        character = item,
                        context = LocalContext.current
                    )
                }
            }
        }
    }
}