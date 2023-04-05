package com.example.marvelapp


sealed class Items_menu (
    val icon: Int,
    val title: String,
    val ruta: String
        ){

    object CharactersPage: Items_menu(
        R.drawable.baseline_person_24,
        title = "Personajes",
        ruta ="pantalla1")
    object ComicsPage: Items_menu(R.drawable.baseline_comic_book_24,
        title = "Comics", ruta="pantalla2")
    object SeriesPage: Items_menu(R.drawable.baseline_serie_tv_24,
        title = "Series", ruta="pantalla3")
}
