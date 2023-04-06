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
import com.example.marvelapp.model.comic.Comic
import com.example.marvelapp.model.comic.ComicDataContainer
import com.example.marvelapp.model.comic.ComicDataWrapper
import com.example.marvelapp.model.comic.ComicInfoTable
import com.example.marvelapp.model.common.CategoryList
import com.example.marvelapp.model.common.Thumbnail
import com.example.marvelapp.network.MarvelAPI
import com.example.marvelapp.repository.MarvelRepository
import com.example.marvelapp.util.Common
import com.example.marvelapp.view.comic.ComicItem
import kotlinx.coroutines.launch

class ComicViewModel : ViewModel() {
    val common = Common()
    var comicListResponse: ComicDataWrapper by mutableStateOf(
        ComicDataWrapper(
            0,
            "",
            "",
            "",
            "",
            null,
            ""
        )
    )

    lateinit var readAllComicData: LiveData<List<ComicInfoTable>>
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
        readAllComicData = repository.readAllComicData
    }

    private fun insertComicIntoDatabase(comic: ComicInfoTable) {
        viewModelScope.launch {
            repository.addComic(comic)
        }
    }

    fun getComicListFromAPI() {
        viewModelScope.launch {
            val marvelAPI = MarvelAPI.getInstance()
            try {
                val comicList = marvelAPI.getComics(ts = "tsor")
                processComicDataFromAPItoDatabase(comicList)
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    private fun processComicDataFromAPItoDatabase(comicList: ComicDataWrapper) {
        comicListResponse = comicList
        comicList.data!!.results.forEach {
            val comicRoomInfo = ComicInfoTable(
                id = it.id,
                title = it.title,
                description = it.description?:"",
                pageCount = it.pageCount,
                thumbnail = it.thumbnail.path + "$$" + it.thumbnail.extension,
                characters = common.parseItemData(it.characters!!.items),
                creators = common.parseItemData(it.creators!!.items),
                events = common.parseItemData(it.events!!.items),
                series = common.parseItemData(it.series!!.items),
                stories = common.parseItemData(it.stories!!.items),
            )
            insertComicIntoDatabase(comicRoomInfo)
        }
    }

    fun getLocalDatabaseData() {
        viewModelScope.launch {
            if (readAllComicData.value != null) {
                val comic = mutableListOf<Comic>()
                loadAllComicsToList(readAllComicData.value!!, comic)
                val tmpComicDataContainer = ComicDataContainer(0, 10, 0, comic, 10)
                val tmpComicDataWrapper =
                    ComicDataWrapper(0, "", "", "", "", tmpComicDataContainer, "")
                comicListResponse = tmpComicDataWrapper
            }
        }
    }

    private fun loadAllComicsToList(
        value: List<ComicInfoTable>,
        comic: MutableList<Comic>
    ) {
        value.forEach {
            comic.add(
                Comic(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    pageCount = it.pageCount,
                    thumbnail = Thumbnail(
                        path = it.thumbnail.split("$$")[0],
                        extension = it.thumbnail.split("$$")[1]
                    ),
                    characters = CategoryList(
                        0,
                        "",
                        items = common.splitCategoryData(it.characters),
                        0
                    ),
                    creators = CategoryList(
                        0,
                        "",
                        items = common.splitCategoryData(it.creators),
                        0
                    ),
                    events = CategoryList(0, "", items = common.splitCategoryData(it.events), 0),
                    series = CategoryList(0, "", items = common.splitCategoryData(it.series), 0),
                    stories = CategoryList(0, "", items = common.splitCategoryData(it.stories), 0),
                )
            )
        }
    }

    @Composable
    fun ComicList(
        comicList: ComicDataWrapper,
    ) {
        LazyColumn {
            comicList.data?.let { it ->
                itemsIndexed(items = it.results) { _, item ->
                    ComicItem(
                        comic = item,
                        context = LocalContext.current
                    )
                }
            }
        }
    }
}