package com.io.gazette.common.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GazetteButton(
    buttonTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = { onClick.invoke() },
        modifier = modifier
            .fillMaxWidth(0.85f)
            .size(48.dp)

    ) {
        Text(text = buttonTitle)
    }
}


@Preview
@Composable
fun GazetteButtonPreview() {
    GazetteButton(buttonTitle = "Button", onClick = { })
}