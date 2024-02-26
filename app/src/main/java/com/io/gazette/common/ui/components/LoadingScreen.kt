package com.io.gazette.common.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.io.gazette.common.ui.Pixel6APreview
import com.io.gazette.common.ui.theme.GazetteTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}


@Pixel6APreview
@Composable
fun LoadingScreenPreview() {
    GazetteTheme {
        LoadingScreen(modifier = Modifier.fillMaxSize())
    }

}