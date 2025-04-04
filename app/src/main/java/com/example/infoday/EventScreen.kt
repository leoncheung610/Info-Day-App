package com.example.infoday

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.infoday.ui.theme.InfoDayTheme

data class Event(val title: String, val deptId: String, var saved: Boolean) {
    companion object {
        val data = listOf(
            Event(title = "Career Talks", deptId = "COMS", saved = false),
            Event(title = "Guided Tour", deptId = "COMS", saved = true),
            Event(title = "MindDrive Demo", deptId = "COMP", saved = false),
            Event(title = "Project Demo", deptId = "COMP", saved = false)
        )
    }
}
@Composable
fun EventScreen(deptId: String) {
    LazyColumn {
        items(Event.data.filter{it.deptId == deptId}) { event ->
            ListItem(
                headlineContent = { Text(event.title) }
            )
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventPreview() {
    InfoDayTheme {
        EventScreen("COMP")
    }
}