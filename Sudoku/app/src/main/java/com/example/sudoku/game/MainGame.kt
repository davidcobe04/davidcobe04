package com.example.sudoku.game

import android.graphics.fonts.FontStyle
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.DataSource.datasource.difficulty
import com.example.sudoku.DataSource.datasource.failures
import com.example.sudoku.DataSource.datasource.generateGrid
import com.example.sudoku.DataSource.datasource.lists
import com.example.sudoku.DataSource.datasource.myMap
import com.example.sudoku.DataSource.datasource.numberSelected
import com.example.sudoku.DataSource.datasource.hiddenPositions
import com.example.sudoku.DataSource.datasource.score
import com.example.sudoku.R

/**
 * Short game example. DAM. PMYDM
 *
 * @author: Antonio David Cotrina Becerra. 2024
 *
 */

@Composable
fun adcotrinab_game() {
    val navController = rememberNavController()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        NavHost(navController = navController, startDestination = "selectDifficulty") {
            composable("selectDifficulty") {
                SelectDifficulty(onChangeScreen = { navController.navigate(it) })
            }
            composable("game") {
                DrawGrid(onChangeScreen = { navController.navigate(it) })
            }
        }
    }
}

@Composable
fun GenerateTextField(
    list: MutableList<Int>,
    index: Int,
    keyboardController: SoftwareKeyboardController?,
    fillMap: (String) -> Unit
) {
    var value: String by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var failed by rememberSaveable { mutableStateOf(false) }
    var correct by rememberSaveable { mutableStateOf(false) }

    TextField(value = value,
        onValueChange = {},
        enabled = false,
        modifier = Modifier
            .width(40.dp)
            .clickable {
                value = numberSelected
                myMap["${lists.indexOf(list)}$index"] = value

                when (difficulty) {
                    "easy" -> if (myMap.count() == 10 && score < 1000) {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.complete),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    } else if (myMap.count() == 10 && score == 1000) {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.perfect),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }

                    "medium" -> if (myMap.count() == 30 && score < 3000) {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.complete),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    } else if (myMap.count() == 30 && score == 3000) {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.perfect),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }

                    "difficult" -> if (myMap.count() == 60 && score < 6000) {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.complete),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    } else if (myMap.count() == 60 && score == 6000) {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.perfect),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }

            },
        textStyle = TextStyle(
            color =
            if (value != "") {
                numberSelected = ""
                if (list[index] == value.toInt()) {
                    if(!correct){
                        score += 100
                        correct = true
                        failed = false
                    }
                    Color.Black
                } else {
                    if(!failed){
                        failures += 1
                        failed = true
                        correct = false
                        if (score - 50 < 0){
                            score = 0
                        }else{
                            score -= 50
                        }
                    }
                    Color.Red
                }
            } else {
                Color.Black
            },
            fontSize = 16.sp
        ),
        keyboardActions = KeyboardActions(onDone = { fillMap(value); keyboardController?.hide() }
        )
    )
}

@Composable
fun DrawGrid(onChangeScreen: (String) -> Unit) {
    var reloadGame by rememberSaveable { mutableStateOf(true) }
    var keyboardController = LocalSoftwareKeyboardController.current

    if (reloadGame) {
        myMap.clear()
        score = 0
        failures = 0
        generateGrid()
        reloadGame = false
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start=30.dp)) {
            Spacer(Modifier.height(40.dp))
            Text(text = stringResource(R.string.score, score), fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end=30.dp)) {
            Spacer(Modifier.height(40.dp))
            Text(text = stringResource(R.string.failures, failures), fontWeight = FontWeight.Bold)
        }
    }

    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(Modifier.height(80.dp))

            for (list in lists) {
                Row {
                    for (i in 0 until lists.size) {
                        Column(Modifier.border(1.dp, Color.LightGray)) {
                            val index = Pair(lists.indexOf(list), i)
                            if(index in hiddenPositions){
                                GenerateTextField(
                                    list,
                                    i,
                                    keyboardController,
                                    fillMap = { value ->
                                        myMap["${lists.indexOf(list)}$i"] = value

                                    })
                            } else {
                                TextField(
                                    value =
                                    list[i].toString(),
                                    onValueChange = {},
                                    enabled = false,
                                    modifier = Modifier.width(40.dp),
                                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            Spacer(Modifier.height(80.dp))

            for (i in 0 until 3) {
                Row {
                    for (i in 0 until 3) {
                        Column(
                            Modifier
                                .border(1.dp, Color.Black)
                                .width(120.dp)
                                .height(168.dp)
                        ) {
                            Text(text = "")
                        }
                    }
                }
            }
        }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Spacer(Modifier.height(450.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            for (i in 1 until 10) {
                if (i == 1) {
                    Text(
                        text = "$i ",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { numberSelected = i.toString() })
                } else {
                    Text(
                        text = " $i ",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { numberSelected = i.toString() })
                }

            }
        }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
        Spacer(Modifier.height(30.dp))

        Row{
            Button(
                onClick = {
                    reloadGame = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.play_again))
            }

            Spacer(Modifier.width(40.dp))

            Button(
                onClick = {
                    onChangeScreen("selectDifficulty")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.change_difficulty))
            }
        }
        Spacer(Modifier.height(50.dp))
    }
}