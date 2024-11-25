package com.adcotrinab.sudoku.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adcotrinab.sudoku.model.Game
import com.adcotrinab.sudoku.ui.MainListScreen
import com.adcotrinab.sudoku.ui.gamescreens.Cotrinad
import com.adcotrinab.sudoku.ui.gamescreens.Cotrinad_end
import com.adcotrinab.sudoku.ui.gamescreens.Cotrinad_game
import com.adcotrinab.sudoku.viewmodels.GamesViewModel
import com.adcotrinab.sudoku.viewmodels.PlayersViewModel


@Composable
fun Navigation(navController: NavHostController) {
    val gamesViewModel: GamesViewModel = viewModel()
    val games: List<Game> by gamesViewModel.games.collectAsState()
    val playersViewModel: PlayersViewModel = viewModel()
    var selectedPackageGame by rememberSaveable { mutableStateOf("Datasource.emptyGame") }
    //Ojo con Nav Controller, que llama a clear en los viewmodels
    NavHost(
        navController = navController,
        startDestination = Destinations.MainListScreen.route,
    ) {
        composable(route = Destinations.MainListScreen.route) {
            MainListScreen(
                games, {
                        game -> navController.navigate(game.packageName)
                        Log.d("SHORT",  game.packageName)
                        selectedPackageGame = game.packageName })
        }


        //** Zona de rutas de juegos ****************************************************************

        //* cotrinad ***************************************************************************
        composable(route = "cotrinad") {
            val game: Game = games.single{(it.packageName == selectedPackageGame)}
            Cotrinad(game,
                onStart = {navController.navigate(Destinations.Cotrinad_game.route)})
        }
        composable(route = Destinations.Cotrinad_game.route) {
            val game: Game = games.single{(it.packageName == selectedPackageGame)}
            Cotrinad_game(game,playersViewModel, onGameEnd = {navController.navigate(Destinations.Cotrinad_end.route)}, gamesViewModel)
        }
        composable(route = Destinations.Cotrinad_end.route) {
            val game: Game = games.single{(it.packageName == selectedPackageGame)}
            Cotrinad_end(game, playersViewModel, onReturn = {navController.navigate(Destinations.MainListScreen.route)}, onPlayAgain = {navController.navigate(Destinations.Cotrinad_game.route)})
        }
    }

}

