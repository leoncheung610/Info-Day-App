package com.example.infoday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.infoday.DataStoreInstance.DARK_MODE
import com.example.infoday.ui.theme.InfoDayTheme


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkMode by DataStoreInstance.getBooleanPreferences(this, DARK_MODE)
                .collectAsState(initial = false)
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            InfoDayTheme (darkTheme = darkMode == true){
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("HKBU InfoDay App") },
                            navigationIcon = {
                                val backStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = backStackEntry?.destination?.route

                                if (currentRoute?.startsWith("event/") == true) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            },
                        )
                    },
                    bottomBar = { BottomNavBar(navController) }
                ) { innerPadding ->
                    // Replace Greeting() with NavHost
                    NavHost(
                        navController = navController, // reference to your nav controller
                        startDestination = "home", // default screen to display
                        modifier = Modifier.padding(innerPadding)

                    ) {
                        composable("Itin"){ItineraryScreen(snackbarHostState)}
                        composable("map") { MapScreen() }
                        composable("home") { // composable for "home" screen
                            Greeting("Android") // screen to display for "home"
                        }
                        composable("info") { // composable for "info" screen
                            InfoScreen() // screen to display for "info"
                        }
                        // Composable for DeptScreen
                        composable("dept") {
                            DeptScreen(navController) }
// Composable for EventScreen with an argument
                        composable("event/{deptId}") { backStackEntry ->
                            // Extract the department id from the NavBackStackEntry's arguments
                            EventScreen(snackbarHostState, backStackEntry.arguments?.getString("deptId")?:"")
                        }
                        composable("home") { FeedScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    // A list of items
    val items = listOf("Home", "Events", "Itin", "Map", "Info")
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                modifier = Modifier.testTag(item),
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    when (index) {
                        0 -> navController.navigate("home")
                        // NavHostComposable
                        1 -> navController.navigate("dept")
                        2 -> navController.navigate("Itin")
                        3 -> navController.navigate("map")
                        4 -> navController.navigate("info")
                    }
                }
            )
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InfoDayTheme {
        Greeting("Android")
    }
}