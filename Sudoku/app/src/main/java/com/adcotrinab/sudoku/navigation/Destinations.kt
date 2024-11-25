package com.adcotrinab.sudoku.navigation

sealed class Destinations(val route: String) {
    object IntroScreen     : Destinations(route = "intro_screen")
    object GameScreen      : Destinations(route = "game_screen")
    object EndScreen       : Destinations(route = "end_screen")
    object MainListScreen  : Destinations(route = "list_screen")

    //Rutas de juegos
    object Cotrinad  : Destinations(route = "cotrinad")
    object Cotrinad_game  : Destinations(route = "cotrinad_game")
    object Cotrinad_end  : Destinations(route = "cotrinad_end")
}

