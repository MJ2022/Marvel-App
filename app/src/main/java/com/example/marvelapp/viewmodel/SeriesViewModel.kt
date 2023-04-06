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
import com.example.marvelapp.model.common.CategoryList
import com.example.marvelapp.model.common.Thumbnail
import com.example.marvelapp.model.series.Series
import com.example.marvelapp.model.series.SeriesDataContainer
import com.example.marvelapp.model.series.SeriesDataWrapper
import com.example.marvelapp.model.series.SeriesInfoTable
import com.example.marvelapp.network.MarvelAPI
import com.example.marvelapp.repository.MarvelRepository
import com.example.marvelapp.util.Common
import com.example.marvelapp.view.series.SeriesItem
import kotlinx.coroutines.launch

class SeriesViewModel : ViewModel() {
    val common = Common()
    var seriesListResponse: SeriesDataWrapper by mutableStateOf(
        SeriesDataWrapper(
            0,
            "",
            "",
            "",
            "",
            null,
            ""
        )
    )

    lateinit var readAllSeriesData: LiveData<List<SeriesInfoTable>>
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
        readAllSeriesData = repository.readAllSeriesData
    }

    private fun insertSeriesIntoDatabase(series: SeriesInfoTable) {
        viewModelScope.launch {
            repository.addSeries(series)
        }
    }

    fun getSeriesListFromAPI() {
        viewModelScope.launch {
            val marvelAPI = MarvelAPI.getInstance()
            try {
                val seriesList = marvelAPI.getSeries(ts = "tsor")
                processSeriesDataFromAPItoDatabase(seriesList)
            } catch (e: java.lang.Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    private fun processSeriesDataFromAPItoDatabase(seriesList: SeriesDataWrapper) {
        seriesListResponse = seriesList
        seriesList.data!!.results.forEach {
            val seriesRoomInfo = SeriesInfoTable(
                id = it.id,
                title = it.title,
                description = it.description ?: "",
                thumbnail = it.thumbnail.path + "$$" + it.thumbnail.extension,
                comics = common.parseItemData(it.comics!!.items),
                stories = common.parseItemData(it.stories!!.items),
                events = common.parseItemData(it.events!!.items),
                characters = common.parseItemData(it.characters!!.items),
                creators = common.parseItemData(it.creators!!.items)
            )
            insertSeriesIntoDatabase(seriesRoomInfo)
        }
    }

    fun getLocalDatabaseData() {
        viewModelScope.launch {
            if (readAllSeriesData.value != null) {
                val series = mutableListOf<Series>()
                loadAllSeriesToList(readAllSeriesData.value!!, series)
                val tmpSeriesDataContainer = SeriesDataContainer(0, 10, 0, series, 10)
                val tmpSeriesDataWrapper =
                    SeriesDataWrapper(0, "", "", "", "", tmpSeriesDataContainer, "")
                seriesListResponse = tmpSeriesDataWrapper
            }
        }
    }

    private fun loadAllSeriesToList(
        value: List<SeriesInfoTable>,
        series: MutableList<Series>
    ) {
        value.forEach {
            series.add(
                Series(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    thumbnail = Thumbnail(
                        path = it.thumbnail.split("$$")[0],
                        extension = it.thumbnail.split("$$")[1]
                    ),
                    comics = CategoryList(0, "", items = common.splitCategoryData(it.comics), 0),
                    stories = CategoryList(0, "", items = common.splitCategoryData(it.stories), 0),
                    events = CategoryList(0, "", items = common.splitCategoryData(it.events), 0),
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

                    )
            )
        }
    }

    @Composable
    fun SeriesList(
        seriesList: SeriesDataWrapper,
    ) {
        LazyColumn {
            seriesList.data?.let { it ->
                itemsIndexed(items = it.results) { _, item ->
                    SeriesItem(
                        series = item,
                        context = LocalContext.current
                    )
                }
            }
        }
    }
}