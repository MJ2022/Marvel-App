package com.example.marvelapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.marvelapp.Items_menu.*
import com.example.marvelapp.model.character.CharacterDataWrapper
import com.example.marvelapp.ui.theme.MarvelAppTheme
import com.example.marvelapp.view.CharacterItem
import com.example.marvelapp.viewmodel.CharacterViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/*
@Composable
fun NavigationHost(navController: NavHostController, characterViewModel: CharacterViewModel){

    NavHost(navController = navController,
        startDestination = CharactersPage.ruta,){
        composable (CharactersPage.ruta){
           var refreshing by remember { mutableStateOf(false) }
            LaunchedEffect(refreshing) {
                if (refreshing) {
                    characterViewModel.getCharacterListFromAPI()
                    refreshing = false
                }
            }

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = {
                    refreshing = true
                },
            ) {
                MarvelAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black
                    ) {
                        characterViewModel.initData(LocalContext.current.applicationContext)

                        //observing character data changes to update state
                        characterViewModel.readAllCharacterData.observe(it) {
                            if (characterViewModel.readAllCharacterData.value?.isEmpty() == true)
                                characterViewModel.getCharacterListFromAPI()
                            else {
                                characterViewModel.getLocalDatabaseData()
                            }
                        }

                        //call to show Character List
                        CharacterList(
                            characterViewModel.characterListResponse,
                        )
                    }
                }

        }}
        composable (ComicsPage.ruta){
            LazyColumn {
                items(Constant.getComics()){
                    Card(it)
                }
            }
        }
        composable (SeriesPage.ruta){
            LazyColumn {
                items(Constant.getSeries()){
                    Card(it)
                }
            }
        }
    }
}

//function display list of characters
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
}*/

