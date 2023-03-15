package com.example.marvelapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.marvelapp.ui.theme.MarvelAppTheme
import com.example.marvelapp.Items_menu.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PantallaPrincipal()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PantallaPrincipal() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val navigation_item = listOf(
        Pantalla1,
        Pantalla2,
        Pantalla3
    )

    Scaffold(
        scaffoldState= scaffoldState,
        bottomBar = {NavegacionInferior(navController, navigation_item)},
        topBar = { Toolbar() }
    ) {
        NavigationHost(navController)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val entrada by navController.currentBackStackEntryAsState()
    return entrada?.destination?.route
}

@Composable
fun Toolbar(){
    TopAppBar(
        title= { Text(text ="Marvel Consultant",color = colorResource(id = R.color.white))},
        backgroundColor = colorResource(id = R.color.red)
    )
}

@Composable
fun NavegacionInferior(
    navController : NavHostController,
    menu_items : List<Items_menu>)
{
    BottomAppBar(){
        BottomNavigation(

        ){
            val currentRoute = currentRoute(navController = navController)
            menu_items.forEach {item ->
                BottomNavigationItem(
                    selected= currentRoute == item.ruta,
                    onClick = {navController.navigate(item.ruta)},
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = {Text(item.title)}
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