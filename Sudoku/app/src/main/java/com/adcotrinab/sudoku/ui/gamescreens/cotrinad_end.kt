package com.adcotrinab.sudoku.ui.gamescreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adcotrinab.sudoku.model.Game
import com.adcotrinab.sudoku.model.Player
import com.adcotrinab.sudoku.ui.ButtonBackToList
import com.adcotrinab.sudoku.ui.ButtonPlayAgain
import com.adcotrinab.sudoku.ui.GameInfo
import com.adcotrinab.sudoku.ui.TextSubtitle
import com.adcotrinab.sudoku.viewmodels.PlayersViewModel

@Composable
fun Cotrinad_end(
    game: Game,
    playersViewModel: PlayersViewModel,
    onReturn: () -> Unit,
    onPlayAgain: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GameInfo(game)
        CotrinaDGameEnd(playersViewModel = playersViewModel)
        ButtonPlayAgain(onClick = onPlayAgain)
        ButtonBackToList(onClick = onReturn)
    }
}

@Composable
fun CotrinaDGameEnd(playersViewModel: PlayersViewModel) {
    val player1: Player by playersViewModel.player1.collectAsState()
    val player2: Player by playersViewModel.player2.collectAsState()

    TextSubtitle("Fin de la partida. Puntuaciones")
    Text("${player1.name}: ${player1.score}")
    Spacer(Modifier.size(6.dp))
    Text( "${player2.name}: ${player2.score}")
    Spacer(Modifier.size(24.dp))
    val winner: String = if (player1.score>player2.score) player1.name else player2.name
    TextSubtitle("Ganador/a: ${winner}")
}

/*********************************************************************************************/
@Preview
@Composable
fun Cotrinad_end_test() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GameInfo(Game(0,"Test","Joe Doe","",0,0,0))
        //PaniaguaAGameEnd(playersViewModel = viewModel())
        ButtonPlayAgain(onClick = { } )
        ButtonBackToList(onClick = { } )
    }
}