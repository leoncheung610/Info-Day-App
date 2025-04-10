package com.example.infoday

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.infoday.ui.theme.InfoDayTheme
import kotlinx.coroutines.launch

import androidx.compose.runtime.getValue

@Entity(tableName = "event")
data class Event(
    @PrimaryKey val id: Int, val title: String, val deptId: String, var saved: Boolean
)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventScreen(snackbarHostState: SnackbarHostState, deptId: String?) {

    val eventDao = EventDatabase.getInstance(LocalContext.current).eventDao()
    val events by eventDao.getAll().collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        items(events.filter { it.deptId == deptId }) { event ->
            ListItem(
                headlineContent = { Text(event.title) },
                modifier = Modifier.combinedClickable(
                    onClick = { /* Handle click */ },
                    onLongClick = {
                        coroutineScope.launch {
                            event.saved = true
                            eventDao.update(event)
                            snackbarHostState.showSnackbar(
                                "Event '${event.title}' has been added to itinerary."
                            )
                        }
                    }
                )
            )
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventPreview() {

}