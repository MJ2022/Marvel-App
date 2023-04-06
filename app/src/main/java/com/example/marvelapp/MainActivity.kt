package com.example.marvelapp


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.marvelapp.Items_menu.*
import com.example.marvelapp.ui.theme.MarvelAppTheme
import com.example.marvelapp.viewmodel.CharacterViewModel
import com.example.marvelapp.viewmodel.ComicViewModel
import com.example.marvelapp.viewmodel.SeriesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class MainActivity : ComponentActivity() {
    private val characterViewModel by viewModels<CharacterViewModel>()
    private val comicViewModel by viewModels<ComicViewModel>()
    private val seriesViewModel by viewModels<SeriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PantallaPrincipal()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun PantallaPrincipal() {
        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
        val navigation_item = listOf(
            CharactersPage,
            ComicsPage,
            SeriesPage
        )
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = { NavegacionInferior(navController, navigation_item) },
            topBar = { Header() }
        ) {
            NavigationHost(navController)
        }
    }

    @Composable
    fun NavigationHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = CharactersPage.ruta) {
            composable(CharactersPage.ruta) {
                var refreshing by remember { mutableStateOf(false) }
                LaunchedEffect(refreshing) {
                    if (refreshing) {
                        characterViewModel.getCharacterListFromAPI()
                        refreshing = false
                    }
                }
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = refreshing),
                    onRefresh = { refreshing = true },
                ) {
                    MarvelAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Black
                        ) {
                            characterViewModel.initData(LocalContext.current.applicationContext)
                            characterViewModel.readAllCharacterData.observe(this@MainActivity) {
                                if (characterViewModel.readAllCharacterData.value?.isEmpty() == true)
                                    characterViewModel.getCharacterListFromAPI()
                                else {
                                    characterViewModel.getLocalDatabaseData()
                                }
                            }
                            characterViewModel.CharacterList(characterList = characterViewModel.characterListResponse)
                        }
                    }

                }
            }
            composable(ComicsPage.ruta) {
                var refreshing by remember { mutableStateOf(false) }
                LaunchedEffect(refreshing) {
                    if (refreshing) {
                        comicViewModel.getComicListFromAPI()
                        refreshing = false
                    }
                }
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = refreshing),
                    onRefresh = { refreshing = true },
                ) {
                    MarvelAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Black
                        ) {
                            comicViewModel.initData(LocalContext.current.applicationContext)
                            comicViewModel.readAllComicData.observe(this@MainActivity) {
                                if (comicViewModel.readAllComicData.value?.isEmpty() == true)
                                    comicViewModel.getComicListFromAPI()
                                else {
                                    comicViewModel.getLocalDatabaseData()
                                }
                            }
                            comicViewModel.ComicList(comicList = comicViewModel.comicListResponse)
                        }
                    }
                }
            }
            composable(SeriesPage.ruta) {
                var refreshing by remember { mutableStateOf(false) }
                LaunchedEffect(refreshing) {
                    if (refreshing) {
                        seriesViewModel.getSeriesListFromAPI()
                        refreshing = false
                    }
                }
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = refreshing),
                    onRefresh = { refreshing = true },
                ) {
                    MarvelAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Black
                        ) {
                            seriesViewModel.initData(LocalContext.current.applicationContext)
                            seriesViewModel.readAllSeriesData.observe(this@MainActivity) {
                                if (seriesViewModel.readAllSeriesData.value?.isEmpty() == true)
                                    seriesViewModel.getSeriesListFromAPI()
                                else {
                                    seriesViewModel.getLocalDatabaseData()
                                }
                            }
                            seriesViewModel.SeriesList(seriesList = seriesViewModel.seriesListResponse)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun currentRoute(navController: NavHostController): String? {
        val entrada by navController.currentBackStackEntryAsState()
        return entrada?.destination?.route
    }

    @Composable
    fun NavegacionInferior(
        navController: NavHostController,
        menu_items: List<Items_menu>
    ) {
        BottomAppBar() {
            BottomNavigation(

            ) {
                val currentRoute = currentRoute(navController = navController)
                menu_items.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.ruta,
                        onClick = { navController.navigate(item.ruta) },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PantallaPrincipalPreview() {
        MarvelAppTheme {
            PantallaPrincipal()
        }
    }
}