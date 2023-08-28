package com.io.gazette.common.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.io.gazette.domain.models.NewsCategory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCategories(
    modifier: Modifier = Modifier,
    onCategorySelected: (category: NewsCategory) -> Unit,
) {

    val selected: NewsCategory = NewsCategory.World

    var selectedCategory by rememberSaveable {
        mutableStateOf(selected)
    }


    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        FilterChip(
            selected = selectedCategory is NewsCategory.World,
            onClick = {
                selectedCategory = NewsCategory.World
                onCategorySelected(NewsCategory.World)

            },
            label = {
                Text(text = "World")
            }
        )

        FilterChip(
            selected = selectedCategory is NewsCategory.Business,
            onClick = {
                selectedCategory = NewsCategory.Business
                onCategorySelected(NewsCategory.Business)
            },
            label = {
                Text(text = "Business")
            }
        )

        FilterChip(
            selected = selectedCategory is NewsCategory.Health,
            onClick = {
                selectedCategory = NewsCategory.Health
                onCategorySelected(NewsCategory.Health)
            },
            label = {
                Text(text = "Health")
            }
        )

        FilterChip(
            selected = selectedCategory is NewsCategory.Sports,
            onClick = {
                selectedCategory = NewsCategory.Sports
                onCategorySelected(NewsCategory.Sports)
            },
            label = {
                Text(text = "Sports")
            }
        )


    }


}


@Preview
@Composable
fun NewsCategoriesPreview(

) {

    NewsCategories(
        onCategorySelected = { _ -> }
    )
}