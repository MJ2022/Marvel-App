package com.example.marvelapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import coil.size.Scale

@Composable
fun Header() {
    Box() {
        Image(
            painterResource(R.drawable.logo_marvel),
            contentDescription = "logo"
        )


    }
}
