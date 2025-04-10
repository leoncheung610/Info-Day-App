package com.example.infoday

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

import androidx.compose.runtime.getValue

@Composable
fun ItineraryScreen(snackbarHostState: SnackbarHostState) {
    val eventDao = EventDatabase.getInstance(LocalContext.current).eventDao()
    val events by eventDao.getItinerary().collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        items(events) { event ->
            ListItem(
                headlineContent = { Text(event.title) },
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            coroutineScope.launch {
                                event.saved = false
                                eventDao.update(event)
                                snackbarHostState.showSnackbar(
                                    "Event '${event.title}' has been removed from itinerary."
                                )
                            }
                        }
                    )
                } ,

                )
            HorizontalDivider()
        }
    }
}