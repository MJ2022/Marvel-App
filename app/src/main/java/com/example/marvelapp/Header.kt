package com.example.marvelapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun Header() {
    Box(){
        AsyncImage(model ="https://www.freepnglogos.com/uploads/marvel-logo-png/new-marvel-studios-logo-debuted-marvelstudios-3.png", contentDescription = "Marvel Logo")

    }
}
