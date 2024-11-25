package com.adcotrinab.sudoku.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adcotrinab.sudoku.R
import com.adcotrinab.sudoku.model.Game
import com.adcotrinab.sudoku.populateGames

@Composable
fun MainListScreen(games: List<Game>, onStart: (Game) -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.size(24.dp))
        Text("SHORT DAM GAMES", fontSize = 24.sp)
        Spacer(Modifier.size(2.dp))
        Text("Seleccione un juego para comenzar")
        Spacer(Modifier.size(12.dp))

        GameList(
            games,
            onStart = { game -> onStart(game) })
    }
}

@Composable
fun GameList(games: List<Game>, onStart: (Game) -> Unit) {
    val games = games.toMutableList()
    LazyColumn(Modifier.fillMaxWidth()) {
        items(items = games) { game ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                border = BorderStroke(1.dp, Color.LightGray),

            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                onClick = { onStart(game) }
            ) {
                ListItem(game)
            }
        }
    }
}

@Composable
fun ListItem(game: Game) {
    Row(Modifier.padding(16.dp)) {
        Icon(
            painter = painterResource(R.drawable.icon_material_esports),
            contentDescription = (null),
            tint = Color(MaterialTheme.colorScheme.secondary.toArgb()),
            modifier = Modifier
                .align(Alignment.Top)
                .size(size = 64.dp)
                .clickable { Log.d("CLICKED", "Click on $game") },
        )
        Column(Modifier.padding(6.dp, 8.dp)) {
            Text(
                text = game.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(0.dp, 2.dp)
            )
            Text(
                text = game.author,
                modifier = Modifier.padding(0.dp, 0.dp)
            )
            Text(
                text = "Jugadores: " + game.numPlayers,
                modifier = Modifier.padding(0.dp, 0.dp)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainListScreen(games = populateGames(), onStart = {})
}
