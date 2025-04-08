package com.example.infoday

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.infoday.ui.theme.InfoDayTheme
import kotlinx.serialization.Serializable

@Serializable
data class Feed(val id: Int, val image: String, val title: String, val detail: String) {
    companion object {
        val data = listOf(
            Feed(
                200,
                "https://cdn.stocksnap.io/img-thumbs/960w/philadelphia-travel_LPDQBLM2A0.jpg",
                " philadelphia-travel ",
                " discount "
            ),
        )
    }
}

@Composable
fun FeedScreen() {
    val feeds by produceState(
        initialValue = emptyList<Feed>(),
        producer = {
            // This runs in a coroutine scope automatically
            value = KtorClient.getFeeds()
        }
    )
    LazyColumn {
        items(feeds) { feed ->
            Card(
                onClick = { /* Do something */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Column {
                    AsyncImage(
                        model = feed.image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(Modifier.fillMaxSize()) {
                        Text(feed.title, Modifier.align(Alignment.Center))
                    }
                }
                HorizontalDivider()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FeedPreview() {
    InfoDayTheme {
        FeedScreen()
    }
}