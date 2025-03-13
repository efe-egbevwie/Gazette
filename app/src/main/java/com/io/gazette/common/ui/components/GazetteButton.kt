package com.io.gazette.common.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GazetteButton(
    buttonTitle: String,
    enabled:Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(6.dp),
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text = buttonTitle)
    }
}


@Preview
@Composable
fun GazetteButtonPreview() {
    GazetteButton(buttonTitle = "Button", enabled = true, onClick = { })
}