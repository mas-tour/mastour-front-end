package com.mastour.mastour.ui.navigation

sealed class Screen(val route: String){
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Search : Screen("search")
    object Detail : Screen("detail/{detailId}"){
        fun createRoute(detailId: String) = "detail/$detailId"
    }
    object History : Screen("history")
    object Matchmaking : Screen("matchmaking")
    object MatchmakingResults : Screen("matchmakingresults")
    object Profile : Screen("profile")
    object Survey : Screen("survey")
    object Results : Screen("results/{resultsId}"){
        fun createRoute(resultsId: String) = "resultsId/$resultsId"
    }
}