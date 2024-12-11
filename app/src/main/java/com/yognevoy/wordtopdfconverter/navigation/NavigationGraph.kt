package com.yognevoy.wordtopdfconverter.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem().navigationItems().first().route,
        modifier = Modifier.padding(paddingValues)
    ) {
        NavigationItem().navigationItems().forEach { item ->
            composable(route = item.route) {
                item.content(navController)
            }
        }
    }
}
