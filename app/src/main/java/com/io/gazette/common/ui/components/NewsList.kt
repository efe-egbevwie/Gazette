package com.io.gazette.common.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import com.io.gazette.domain.models.NewsItem

@Composable
fun NewsList(
    newsItems: List<NewsItem>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    onSaveStoryButtonClicked: (storyUrl: String, storyImageUrl: String?) -> Unit
) {

    val listState = rememberLazyListState()
    LazyColumn(modifier.fillMaxSize(), state = listState) {
        items(newsItems) { newsItem ->
            if (newsItem.isValid()) NewsListItem(
                newsItem = newsItem,
                onItemClick = onItemClick,
                onSaveStoryButtonClicked = onSaveStoryButtonClicked
            )
        }
    }
}


val sampleNewsList = listOf(
    NewsItem(
        title = "The New Chatbots Could Change the World. Can You Trust Them?",
        abstract = "Siri, Google Search, online marketing and your child’s homework will never be the same." +
                " Then there’s the misinformation problem.",
        section = "Business",
        url = "https://www.nytimes.com/2022/12/10/technology/ai-chat-bot-chatgpt.html",
        photoUrl = "https://static01.nyt.com/images/2022/12/12/multimedia/10chatbots-margolis-1-3fe8/10chatbots-margolis-1-3fe8-superJumbo.jpg?quality=75&auto=webp",
        publishedDate = "Dec. 10, 2022",
        writer = "Cade Metz",
    ),
    NewsItem(
        title = "The New Chatbots Could Change the World. Can You Trust Them?",
        abstract = "Siri, Google Search, online marketing and your child’s homework will never be the same." +
                " Then there’s the misinformation problem.",
        section = "Business",
        url = "https://www.nytimes.com/2022/12/10/technology/ai-chat-bot-chatgpt.html",
        photoUrl = "https://static01.nyt.com/images/2022/12/12/multimedia/10chatbots-margolis-1-3fe8/10chatbots-margolis-1-3fe8-superJumbo.jpg?quality=75&auto=webp",
        publishedDate = "Dec. 10, 2022",
        writer = "Cade Metz",
    ),
    NewsItem(
        title = "The New Chatbots Could Change the World. Can You Trust Them?",
        abstract = "Siri, Google Search, online marketing and your child’s homework will never be the same." +
                " Then there’s the misinformation problem.",
        section = "Business",
        url = "https://www.nytimes.com/2022/12/10/technology/ai-chat-bot-chatgpt.html",
        photoUrl = "https://static01.nyt.com/images/2022/12/12/multimedia/10chatbots-margolis-1-3fe8/10chatbots-margolis-1-3fe8-superJumbo.jpg?quality=75&auto=webp",
        publishedDate = "Dec. 10, 2022",
        writer = "Cade Metz",
    ),
    NewsItem(
        title = "The New Chatbots Could Change the World. Can You Trust Them?",
        abstract = "Siri, Google Search, online marketing and your child’s homework will never be the same." +
                " Then there’s the misinformation problem.",
        section = "Business",
        url = "https://www.nytimes.com/2022/12/10/technology/ai-chat-bot-chatgpt.html",
        photoUrl = "https://static01.nyt.com/images/2022/12/12/multimedia/10chatbots-margolis-1-3fe8/10chatbots-margolis-1-3fe8-superJumbo.jpg?quality=75&auto=webp",
        publishedDate = "Dec. 10, 2022",
        writer = "Cade Metz",
    )
)

@Composable
@Preview(
    device = "id:pixel_6a", showSystemUi = true, showBackground = true,
    wallpaper = Wallpapers.NONE
)
fun NewsListPreview() {
    NewsList(newsItems = sampleNewsList, onItemClick = {}, onSaveStoryButtonClicked = { _, _ -> })
}