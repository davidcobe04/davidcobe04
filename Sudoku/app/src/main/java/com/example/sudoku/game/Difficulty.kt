package com.example.sudoku.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.DataSource.datasource.difficulty
import com.example.sudoku.R

@Composable
fun SelectDifficulty(onChangeScreen: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Row {
            Button(
                onClick = {
                    difficulty = "easy"
                    onChangeScreen("game")
                }, modifier = Modifier.height(70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) { Text(stringResource(R.string.easy), fontSize = 20.sp) }
        }

        Spacer(Modifier.height(20.dp))

        Row {
            Button(
                onClick = {
                    difficulty = "medium"
                    onChangeScreen("game")
                }, modifier = Modifier.height(70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) { Text(stringResource(R.string.medium), fontSize = 20.sp) }
        }

        Spacer(Modifier.height(10.dp))

        Row{
            Button(
                onClick = {
                    difficulty = "difficult"
                    onChangeScreen("game")
                }, modifier = Modifier.height(70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) { Text(stringResource(R.string.difficult), fontSize = 20.sp) }
        }
    }
}