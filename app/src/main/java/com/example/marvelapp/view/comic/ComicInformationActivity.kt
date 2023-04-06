package com.example.marvelapp.view.comic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.marvelapp.model.comic.Comic
import com.example.marvelapp.ui.theme.MarvelAppTheme
import com.example.marvelapp.view.CharacterUI

class ComicInformationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val comic = intent.getParcelableExtra("comics") as Comic?
                    if (comic != null) {
                        ComicUI(comic = comic)
                    }
                }
            }
        }
    }
}