package com.mastour.mastour.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mastour.mastour.MainJetpack
import com.mastour.mastour.ui.theme.MasTourTheme
import com.mastour.mastour.ui.theme.md_theme_dark_primaryContainer

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
){
    //TODO: Change Colors matching the Figma Color
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                title = "home",
                icon = Icons.Filled.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "search",
                icon = Icons.Filled.Search,
                screen = Screen.Search
            ),
            NavigationItem(
                title = "history",
                icon = Icons.Filled.History,
                screen = Screen.History
            ),
            NavigationItem(
                title = "profile",
                icon = Icons.Filled.AccountCircle,
                screen = Screen.Profile
            )
        )

        BottomNavigation(modifier
            .background(Color.White)
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))) {
            navigationItem.map {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title
                        )},
                    onClick = {
                        navHostController.navigate(it.screen.route){
                            popUpTo(Screen.Home.route){
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        } },
                    selected = currentRoute == it.screen.route,
                    selectedContentColor = md_theme_dark_primaryContainer,
                    unselectedContentColor = Color(0x507147B1),
                    modifier = modifier.background(Color.White)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun BottomBarPreview(){
    MasTourTheme {
        MainJetpack()
    }
}