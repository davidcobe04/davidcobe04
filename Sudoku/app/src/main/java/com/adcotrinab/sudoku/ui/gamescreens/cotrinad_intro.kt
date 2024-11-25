package com.adcotrinab.sudoku.ui.gamescreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adcotrinab.sudoku.model.Game
import com.adcotrinab.sudoku.ui.ButtonStart
import com.adcotrinab.sudoku.ui.GameInfo
import com.adcotrinab.sudoku.ui.TextSubtitle

@Composable
fun Cotrinad(game: Game, onStart: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GameInfo(game)
        CotrinaDGameDescription()
        ButtonStart(onClick = onStart)
    }
}

@Composable
fun CotrinaDGameDescription() {
    TextSubtitle("Instrucciones")
    Spacer(modifier = Modifier.size(6.dp))

    Column(Modifier.padding(horizontal = 30.dp))
    {
        Text(text = "Se trata de un rompecabezas matemático cuyo objetivo es rellenar una cuadrícula de 9×9 celdas dividida en subcuadrículas de 3×3.\n" +
                "Las celdas se rellenarán con cifras del 1 al 9 partiendo de algunos números ya dispuestos en algunas de ellas.\n" +
                "En cada cuadrícula de 3x3 no se pueden repetir los números.")
    }
}

/*********************************************************************************************/
@Preview
@Composable
fun Cotrinad_intro_test() {
    Column(
        Modifier.fillMaxSize().padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GameInfo(Game(0,"Test","David Cotrina","",1,1,0))
        CotrinaDGameDescription()
        ButtonStart(onClick = {})
    }
}