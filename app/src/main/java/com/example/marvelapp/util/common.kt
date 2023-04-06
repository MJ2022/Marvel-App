package com.example.marvelapp.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelapp.model.common.CategorySummary
import com.example.marvelapp.view.gradientGrayWhite

class Common() {
    fun parseItemData(itemList: List<CategorySummary>?): String {
        var categoryItem = ""
        itemList?.forEach { item -> categoryItem += "${item.name}$$" }
        return categoryItem
    }

    fun splitCategoryData(item: String): MutableList<CategorySummary> {
        val itemList: MutableList<CategorySummary> = mutableListOf()
        item.split("$$").forEach { itemList.add(CategorySummary(name = it, "", "")) }
        itemList.removeLast()
        return itemList
    }

    @Composable
    fun GetDetails( items: List<CategorySummary>) {
        if (items.isNotEmpty()) {
            Column(modifier = Modifier.padding(20.dp, 20.dp)) {
                val comicSize = if (items.size > 5) 4 else items.size - 1
                Text(text = "Related Comics ", color = Color.White, fontSize = 20.sp)
                for (i in 0..comicSize) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        backgroundColor = Color.Gray
                    ) {
                        Text(
                            text = items[i].name, color = Color.White, textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier.background(gradientGrayWhite)
                        )
                    }
                }
            }
        }
    }
}