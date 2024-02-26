package com.io.gazette.common.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.io.gazette.common.ui.theme.GazetteTheme
import com.io.gazette.domain.models.NewsCategory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCategories(
    modifier: Modifier = Modifier,
    selectedCategory: NewsCategory = NewsCategory.WORLD,
    onCategorySelected: (category: NewsCategory) -> Unit,
) {


    MultiChoiceSegmentedButtonRow(modifier = modifier) {
        NewsCategory.allCategories.forEachIndexed { index, category ->
            SegmentedButton(
                checked = category == selectedCategory,
                onCheckedChange = { onCategorySelected(category) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = NewsCategory.allCategories.size
                )
            ) {
                Text(text = category.name)
            }
        }
    }


//    Row(
//        horizontalArrangement = Arrangement.spacedBy(10.dp),
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(10.dp)
//    ) {
//
//        FilterChip(
//            selected = selectedCategory == NewsCategory.WORLD,
//            onClick = {
//                selectedCategory = NewsCategory.WORLD
//                onCategorySelected(NewsCategory.WORLD)
//
//            },
//            label = {
//                Text(text = "World")
//            }
//        )
//
//        FilterChip(
//            selected = selectedCategory == NewsCategory.BUSINESS,
//            onClick = {
//                selectedCategory = NewsCategory.BUSINESS
//                onCategorySelected(NewsCategory.BUSINESS)
//            },
//            label = {
//                Text(text = "Business")
//            }
//        )
//
//        FilterChip(
//            selected = selectedCategory == NewsCategory.HEALTH,
//            onClick = {
//                selectedCategory = NewsCategory.HEALTH
//                onCategorySelected(NewsCategory.HEALTH)
//            },
//            label = {
//                Text(text = "Health")
//            }
//        )
//
//        FilterChip(
//            selected = selectedCategory == NewsCategory.SPORTS,
//            onClick = {
//                selectedCategory = NewsCategory.SPORTS
//                onCategorySelected(NewsCategory.SPORTS)
//            },
//            label = {
//                Text(text = "Sports")
//            }
//        )
//
//
//    }


}


@PreviewLightDark
@Composable
fun NewsCategoriesPreview(

) {
    GazetteTheme {
        Surface {
            NewsCategories(modifier = Modifier.padding(10.dp), onCategorySelected = { _ -> })
        }
    }

}