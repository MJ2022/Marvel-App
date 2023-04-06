package com.example.marvelapp.view.comic

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.marvelapp.R
import com.example.marvelapp.model.comic.Comic

@Composable
fun ComicItem(
    comic: Comic,
    context: Context
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 10.dp)
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Surface {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        val intent = Intent(context, ComicInformationActivity::class.java)
                        intent.putExtra("comics", comic)
                        context.startActivity(intent)
                    }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    val imageUrl = comic.thumbnail.path + "." + comic.thumbnail.extension
                    Image(
                        contentDescription = comic.description,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize(),
                        painter = rememberImagePainter(data = imageUrl,
                            builder = {
                                scale(Scale.FIT)
                                placeholder(R.drawable.logo_marvel)
                            })
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val gradientGrayWhite =
                            Brush.verticalGradient(0f to Color.Transparent, 500f to Color.Black)
                        Text(
                            text = comic.title,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(gradientGrayWhite)
                                .padding(0.dp, 5.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}