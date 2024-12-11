package com.yognevoy.wordtopdfconverter.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.yognevoy.wordtopdfconverter.R
import com.yognevoy.wordtopdfconverter.screens.FileListScreen
import com.yognevoy.wordtopdfconverter.screens.HomeScreen

data class NavigationItem(
    @StringRes val label: Int = 0,
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = "",
    val content: @Composable (NavHostController) -> Unit = {}
) {
    fun navigationItems(): List<NavigationItem> {
        return listOf(
            NavigationItem(
                route = "home_screen",
                label = R.string.menu_item_home,
                icon = Icons.Filled.Home
            ) { navController -> HomeScreen(navController) },
            NavigationItem(
                route = "file_list_screen",
                label = R.string.menu_item_file_list,
                icon = Icons.AutoMirrored.Filled.List
            ) { navController -> FileListScreen(navController) }
        )
    }
}
