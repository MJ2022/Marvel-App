package com.example.marvelapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale


@Composable
fun Card(item: ConstantData) {
    Card(
        modifier = Modifier
            .padding(40.dp,40.dp)
            .clickable { }
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column {
            Row (
                modifier = Modifier
                    .padding(10.dp)
                    .size(250.dp)
                    .fillMaxSize()
            ){
                Image(
                    painter = rememberImagePainter(
                        data = item.img,
                        builder = {
                            scale(Scale.FIT)
                            placeholder(R.drawable.ic_launcher_background)
                        }),
                    contentDescription = item.name
                )
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    item.name,
                    style = MaterialTheme.typography.h3,
                )
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {

                Text(
                    item.description,
                    style = MaterialTheme.typography.h5,
                )
            }
        }
    }
}
