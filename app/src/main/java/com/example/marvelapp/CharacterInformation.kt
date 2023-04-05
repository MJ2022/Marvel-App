package com.example.marvelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.marvelapp.ui.theme.MarvelAppTheme
import com.example.marvelapp.model.character.Character
import com.example.marvelapp.view.CharacterUI

class CharacterInformation : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val character = intent.getParcelableExtra("character") as Character?
                    if (character != null) {
                        CharacterUI(character = character)
                    }
                }
            }
        }
    }
}