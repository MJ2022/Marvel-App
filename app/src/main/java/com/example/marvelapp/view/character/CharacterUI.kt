package com.example.marvelapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.marvelapp.model.character.Character
import com.example.marvelapp.R
import com.example.marvelapp.util.Common

@Composable
fun CharacterUI(character: Character?) {
    Surface(color = Color.Black) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val common = Common()
            GetCharacterImageData(character = character!!)
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                GetDescription(character = character)
                common.GetDetails(character.comics!!.items)
                common.GetDetails(character.events!!.items)
                common.GetDetails(character.series!!.items)
                common.GetDetails(character.stories!!.items)
            }
        }
    }
}

val gradientGrayWhite = Brush.verticalGradient(0f to Color.Transparent, 1500f to Color.Black)

@Composable
fun GetCharacterImageData(character: Character) {
    Card(
        modifier = Modifier
            .padding(8.dp, 10.dp)
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val imageUrl = character.thumbnail.path + "." + character.thumbnail.extension
            Image(
                contentDescription = character.description,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(),
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        scale(Scale.FIT)
                        placeholder(R.drawable.logo_marvel)
                    }
                )
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = character.name,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(gradientGrayWhite)
                        .padding(0.dp, 10.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun GetDescription(character: Character) {
    Text(
        text = character.description.ifEmpty { "Description not available" },
        modifier = Modifier.padding(20.dp, 20.dp),
        fontSize = 18.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Light,
        color = Color.Gray
    )
}
