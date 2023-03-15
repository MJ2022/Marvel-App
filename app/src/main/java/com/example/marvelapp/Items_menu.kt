package com.example.marvelapp

    sealed class Items_menu (
    val icon: Int,
    val title: String,
    val ruta: String
        ){

    object Pantalla1: Items_menu(R.drawable.baseline_person_24,
        title = "Personajes",
        ruta ="pantalla1")
    object Pantalla2: Items_menu(R.drawable.baseline_comic_book_24,
        title = "Comics", ruta="pantalla2")
    object Pantalla3: Items_menu(R.drawable.baseline_serie_tv_24,
        title = "Series", ruta="pantalla3")
}
