package com.example.marvelapp.view.character

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

class CharacterInformationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
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