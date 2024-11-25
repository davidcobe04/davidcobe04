package com.adcotrinab.sudoku.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adcotrinab.sudoku.R
import com.adcotrinab.sudoku.model.Game

/**
 * Generic Components
 *
 * Algunas funciones básicas empleadas en diferentes lugares de la aplicación, principalmente en
 * la introducción de los juegos
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameInfo(game: Game) {
    Spacer(Modifier.size(24.dp))
    Text("SHORT DAM GAMES", fontSize = 24.sp)
    Spacer(Modifier.size(2.dp))
    Text("${game.name.uppercase()}", fontSize = 32.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.size(6.dp))
    // Easter egg
    // Probamos un TooltipBox. Para mostrarlo se hce una pulsación larga sobre el nombre del autor.
    // Más en: https://m3.material.io/components/tooltips/overview
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        state = rememberTooltipState(),
        tooltip = { PlainTooltip(caretSize = TooltipDefaults.caretSize) { Text("Este es el autor del juego, y esto un tooltip") } }
    ) {
        Text("${game.author}")
    }
    Spacer(Modifier.size(48.dp))
}

@Composable
fun TextSubtitle(text: String) {
    Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.size(6.dp))
}

@Composable
fun ButtonStart(onClick: () -> Unit) {
    Spacer(Modifier.size(24.dp))
    ElevatedButton(onClick = onClick) {
        Text(stringResource(R.string.bt_comenzar))

    }
}

@Composable
fun ButtonPlayAgain(onClick: () -> Unit) {
    Spacer(Modifier.size(24.dp))
    ElevatedButton(onClick = onClick) {
        Text(stringResource(R.string.bt_jugar_de_nuevo))
    }
}

@Composable
fun ButtonBackToList(onClick: () -> Unit) {
    Spacer(Modifier.size(24.dp))
    ElevatedButton(onClick = onClick) {
        Text(stringResource(R.string.bt_regresar_a_la_lista))

    }
}