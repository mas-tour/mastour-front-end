package com.mastour.mastour

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mastour.mastour.ui.navigation.BottomBar
import com.mastour.mastour.ui.navigation.Screen
import com.mastour.mastour.ui.screen.category.CategoryScreen
import com.mastour.mastour.ui.screen.detail.DetailScreen
import com.mastour.mastour.ui.screen.history.HistoryScreen
import com.mastour.mastour.ui.screen.homepage.HomePageScreen
import com.mastour.mastour.ui.screen.login.LoginScreen
import com.mastour.mastour.ui.screen.matchmaking.MatchmakingContent
import com.mastour.mastour.ui.screen.matchmaking.MatchmakingResultsScreen
import com.mastour.mastour.ui.screen.profile.ProfileScreen
import com.mastour.mastour.ui.screen.register.RegisterScreen
import com.mastour.mastour.ui.screen.search.SearchScreen
import com.mastour.mastour.ui.screen.splashscreen.SplashScreen
import com.mastour.mastour.ui.screen.survey.SurveyScreen

@Composable
fun MainJetpack(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.Home.route || currentRoute == Screen.Profile.route || currentRoute == Screen.Search.route || currentRoute == Screen.History.route) {
                BottomAppBar(
                    modifier = modifier
                        .height(65.dp)
                        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                    cutoutShape = CircleShape,
                    elevation = 22.dp,
                    backgroundColor = Color.White
                ) {
                    BottomBar(navHostController = navController)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (currentRoute == Screen.Home.route || currentRoute == Screen.Profile.route || currentRoute == Screen.Search.route || currentRoute == Screen.History.route) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.Matchmaking.route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    shape = CircleShape,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.wayang_only),
                        contentDescription = "Matchmaking",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screen.Splash.route) {
                SplashScreen(navHostController = navController)
            }

            composable(Screen.Login.route) {
                LoginScreen(navHostController = navController)
            }
            composable(Screen.Register.route) {
                RegisterScreen(navHostController = navController)
            }
            composable(Screen.Home.route) {
                HomePageScreen(navHostController = navController)
            }
            composable(Screen.History.route) {
                HistoryScreen()
            }
            composable(Screen.Search.route) {
                SearchScreen(moveToGuideDetail = {
                    navController.navigate(Screen.Detail.createRoute(it))
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navHostController = navController)
            }
            composable(Screen.Matchmaking.route) {
                MatchmakingContent(nextOnClicked = {
                    navController.navigate(Screen.Survey.route)
                })
            }
            composable(Screen.Survey.route) {
                SurveyScreen(navHostController = navController)
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("detailId") { type = NavType.StringType }),
            ) {
                val id = it.arguments?.getString("detailId") ?: ""
                DetailScreen(
                    id = id,
                    onBackClicked = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = Screen.Results.route,
                arguments = listOf(
                    navArgument("isCity") { type = NavType.BoolType },
                    navArgument("resultsId") { type = NavType.StringType }
                )
            ) {
                val isCity = it.arguments?.getBoolean("isCity") ?: true
                val id = it.arguments?.getString("resultsId") ?: ""

                CategoryScreen(
                    isCity = isCity,
                    id = id,
                    moveToGuideDetail = { idDetail ->
                        navController.navigate(Screen.Detail.createRoute(idDetail))
                    },
                    onBackClicked = { navController.navigateUp() }
                )
            }

            composable(Screen.PostMatchmaking.route) {
                MatchmakingResultsScreen(moveToGuideDetail = {
                    navController.navigate(Screen.Detail.createRoute(it))
                })
            }

        }
    }
}
