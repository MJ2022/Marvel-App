package com.example.marvelapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.marvelapp.Items_menu.*


@Composable
fun NavigationHost(navController: NavHostController){
    NavHost(navController = navController,
        startDestination = Pantalla1.ruta,){
        composable (Pantalla1.ruta){
            Header()
            LazyColumn {
                items(Constant.getPersonajes()){
                    Card(it)
                }
            }

        }
        composable (Pantalla2.ruta){
            Header()
            LazyColumn {
                items(Constant.getComics()){
                    Card(it)
                }
            }
        }
        composable (Pantalla3.ruta){
            Header()
            LazyColumn {
                items(Constant.getSeries()){
                    Card(it)
                }
            }
        }
    }
}