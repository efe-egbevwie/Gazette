package com.io.gazette.common.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun CloseScreenButton(
    onCloseClicked: () -> Unit
) {
    IconButton(onClick = onCloseClicked) {
        Icon(imageVector = Icons.Filled.Close, tint = MaterialTheme.colorScheme.primary, contentDescription = "Close Screen")
    }
}


@Composable
@Preview(device = "id:pixel_7", showSystemUi = true, showBackground = true)
fun CloseScreenButtonPreview() {
    CloseScreenButton {

    }
}