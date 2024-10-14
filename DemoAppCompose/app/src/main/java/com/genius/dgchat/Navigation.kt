package com.genius.dgchat

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class NavigationItem(
    val route: String, val label: String, val icon: ImageVector
)

@ExperimentalMaterial3Api
@Composable
fun MainNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "home_bottom_navigation"
    ) {
        composable(route = "home_bottom_navigation") {
            BottomNavigationBarPage()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun BottomNavigationBarPage() {
    val bottomNavigationItems = listOf(
        NavigationItem("home", "Screen 1", Icons.Filled.Home),
        NavigationItem("profile", "Screen 2", Icons.Filled.Settings)
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavigationItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController, startDestination = "home",
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            ),
        ) {
            // Define composable screens for bottom navigation
            composable("home") { HomeScreen() }
            composable("profile") {
                ProfileScreen()
            }

        }
    }
}

@Preview
@Composable
fun HomeScreen() {
    Box {
        Text(
            modifier = Modifier
                .fillMaxSize(),
            text = "HOME",
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Sdk user's screen",
                    textAlign = TextAlign.Center
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    onClick = {
                        context.startActivity(Intent(context, ChatActivity::class.java))
                    }
                ) {
                    Text(text = "Show chat")
                }

                Text(
                    modifier = Modifier
                        .clickable { }
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp
                        ),
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                )
            }
        }
    }
}