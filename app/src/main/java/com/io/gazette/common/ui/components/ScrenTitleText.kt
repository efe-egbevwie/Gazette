package com.io.gazette.common.ui.components


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTitleText(title: String, modifier: Modifier = Modifier) {

    Text(
        text = title,
        fontSize = 22.sp,
        modifier = modifier
    )
}


@Preview(device = "id:pixel_7", showSystemUi = true, showBackground = true)
@Composable
fun ScreenTitleTextPreview() {

    ScreenTitleText(title = "Latest News")
}