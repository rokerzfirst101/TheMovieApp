package com.example.themovieapp.ui.main.view.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.themovieapp.ui.theme.HTextColor
import com.example.themovieapp.ui.theme.SurfaceColor

@Composable
fun Chip(text: String) {
    Row(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp, top = 3.dp, bottom = 3.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceColor)
            .padding(horizontal = 15.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = HTextColor,
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun Chip(text: String, @DrawableRes icon: Int?, color: Color?) {
    Row(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp, top = 3.dp, bottom = 3.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceColor)
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null && color != null) {
            Icon(
                modifier = Modifier
                    .size(14.dp),
                painter = painterResource(icon),
                contentDescription = "ChipIcon",
                tint = color,
            )
        }
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = text,
            color = HTextColor,
            style = MaterialTheme.typography.h5
        )
    }
}