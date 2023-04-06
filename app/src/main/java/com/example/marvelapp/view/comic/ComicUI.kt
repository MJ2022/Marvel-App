package com.example.marvelapp.view.comic

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.marvelapp.R
import com.example.marvelapp.model.comic.Comic
import com.example.marvelapp.util.Common
import com.example.marvelapp.view.gradientGrayWhite

@Composable
fun ComicUI(comic: Comic?) {
    Surface(color = Color.Black) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val common = Common()
            GetComicImageData(comic = comic!!)
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                GetDescription(comic = comic)
                common.GetDetails(comic.creators!!.items)
                common.GetDetails(comic.characters!!.items)
                common.GetDetails(comic.events!!.items)
                common.GetDetails(comic.series!!.items)
                common.GetDetails(comic.stories!!.items)
            }
        }
    }
}

@Composable
fun GetComicImageData(comic: Comic) {
    Card(
        modifier = Modifier
            .padding(8.dp, 10.dp)
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val imageUrl = comic.thumbnail.path + "." + comic.thumbnail.extension
            Image(
                contentDescription = comic.description,
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
                    text = comic.title,
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
fun GetDescription(comic: Comic) {
    Text(
        text = comic.description.ifEmpty { "Description not available" },
        modifier = Modifier.padding(20.dp, 20.dp),
        fontSize = 18.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Light,
        color = Color.Gray
    )
    Text(
        text = "Total pages: " + comic.pageCount,
        modifier = Modifier.padding(20.dp, 20.dp),
        fontSize = 18.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Light,
        color = Color.Gray
    )
}



