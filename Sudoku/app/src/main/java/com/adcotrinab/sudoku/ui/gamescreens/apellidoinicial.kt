package com.adcotrinab.sudoku.ui.gamescreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adcotrinab.sudoku.model.Game
import com.adcotrinab.sudoku.ui.ButtonStart
import com.adcotrinab.sudoku.ui.GameInfo

@Composable
fun Apellidoinicial(
    game: Game,
    onStart: () -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GameInfo(game)
        ApellidoInicialGameDescription()
        ButtonStart(onStart)
    }
}

@Composable
fun ApellidoInicialGameDescription() {
    Text("El juego consiste en buscar palabras en un acomodo de letras (aparentemente sin sentido) " +
            "enlazándolas de manera horizontal, vertical o diagonal. ")
}

