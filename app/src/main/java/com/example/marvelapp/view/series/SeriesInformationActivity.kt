package com.example.marvelapp.view.series

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.marvelapp.model.series.Series
import com.example.marvelapp.ui.theme.MarvelAppTheme

class SeriesInformationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val series = intent.getParcelableExtra("series") as Series?
                    if (series != null) {
                        SeriesUI(series = series)
                    }
                }
            }
        }
    }
}