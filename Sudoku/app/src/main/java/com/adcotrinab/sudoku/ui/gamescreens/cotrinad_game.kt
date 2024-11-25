package com.adcotrinab.sudoku.ui.gamescreens

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adcotrinab.sudoku.DataSource.datasource.dificultad
import com.adcotrinab.sudoku.DataSource.datasource.fallos
import com.adcotrinab.sudoku.DataSource.datasource.generateGrid
import com.adcotrinab.sudoku.DataSource.datasource.listas
import com.adcotrinab.sudoku.DataSource.datasource.miMapa
import com.adcotrinab.sudoku.DataSource.datasource.numberSelected
import com.adcotrinab.sudoku.DataSource.datasource.posicionesOcultas
import com.adcotrinab.sudoku.DataSource.datasource.puntuacion
import com.adcotrinab.sudoku.model.Game
import com.adcotrinab.sudoku.viewmodels.GamesViewModel
import com.adcotrinab.sudoku.viewmodels.PlayersViewModel

/**
 * Ejemplo de juego corto. DAM. PMYDM
 *
 * @author: David Cotrina Becerra. 2024
 *
 */

@Composable
fun Cotrinad_game(
    game: Game,
    playersViewModel: PlayersViewModel,
    onGameEnd: () -> Unit,
    gameViewModel: GamesViewModel,
) {
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
fun SelectDifficulty(onChangeScreen: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(320.dp))
        Row {
            Button(
                onClick = {
                    dificultad = "facil"
                    onChangeScreen("game")
                }, modifier = Modifier.height(90.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) { Text("Fácil", fontSize = 25.sp) }
            Spacer(Modifier.width(15.dp))
            Button(
                onClick = {
                    dificultad = "medio"
                    onChangeScreen("game")
                }, modifier = Modifier.height(90.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) { Text("Medio", fontSize = 25.sp) }
            Spacer(Modifier.width(15.dp))
            Button(
                onClick = {
                    dificultad = "dificil"
                    onChangeScreen("game")
                }, modifier = Modifier.height(90.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) { Text("Difícil", fontSize = 25.sp) }
        }
    }
}

@Composable
fun GenerateTextField(
    lista: MutableList<Int>,
    posicion: Int,
    keyboardController: SoftwareKeyboardController?,
    fillMap: (String) -> Unit
) {
    var value: String by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var fallado by rememberSaveable { mutableStateOf(false) }
    var acierto by rememberSaveable { mutableStateOf(false) }

    TextField(value = value,

        onValueChange = {},
        enabled = false,
        modifier = Modifier
            .width(40.dp)
            .clickable {
                value = numberSelected
                miMapa["${listas.indexOf(lista)}$posicion"] = value

                when(dificultad){
                    "facil" -> if(miMapa.count() == 10 && puntuacion < 1000) { Toast.makeText(context, "Sudoku completado.", Toast.LENGTH_SHORT).show() }
                    else if(miMapa.count() == 10 && puntuacion == 1000){ Toast.makeText(context, "Lo has hecho perfecto!!!", Toast.LENGTH_SHORT).show() }

                    "medio" -> if(miMapa.count() == 30 && puntuacion < 3000) { Toast.makeText(context, "Sudoku completado", Toast.LENGTH_SHORT).show() }
                    else if(miMapa.count() == 30 && puntuacion == 3000){ Toast.makeText(context, "Lo has hecho perfecto!!!", Toast.LENGTH_SHORT).show() }

                    "dificil" -> if(miMapa.count() == 60 && puntuacion < 6000) { Toast.makeText(context, "Sudoku completado.", Toast.LENGTH_SHORT).show() }
                    else if(miMapa.count() == 60 && puntuacion == 6000){ Toast.makeText(context, "Lo has hecho perfecto!!!", Toast.LENGTH_SHORT).show() }
                }

            },
        textStyle = TextStyle(
            color =
            if (value != "") {
                numberSelected = ""
                if (lista[posicion] == value.toInt()) {
                    if(!acierto){
                        puntuacion += 100
                        acierto = true
                        fallado = false
                    }
                    Color.Black
                } else {
                    if(!fallado){
                        fallos += 1
                        fallado = true
                        acierto = false
                        if (puntuacion - 50 < 0){
                            puntuacion = 0
                        }else{
                            puntuacion -= 50
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
        miMapa.clear()
        puntuacion = 0
        fallos = 0
        generateGrid()
        reloadGame = false
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start=30.dp)) {
            Text(text = "Puntuación: $puntuacion")
        }
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end=30.dp)) {
            Text(text = "Fallos: $fallos")
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(Modifier.height(30.dp))

            for (lista in listas) {
                Row {
                    for (i in 0 until listas.size) {
                        Column(Modifier.border(1.dp, Color.LightGray)) {
                            val posicion = Pair(listas.indexOf(lista), i)
                            if(posicion in posicionesOcultas){
                                GenerateTextField(
                                    lista,
                                    i,
                                    keyboardController,
                                    fillMap = { valor ->
                                        miMapa["${listas.indexOf(lista)}$i"] = valor

                                    })
                            } else {
                                TextField(
                                    value =
                                    lista[i].toString(),
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

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(30.dp))

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

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(550.dp))
        Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            //Spacer(Modifier.width(6.dp))
            for (i in 1 until 10) {
                if(i == 1){
                    Text(
                        text = "$i ",
                        fontSize = 35.sp,
                        modifier = Modifier.clickable { numberSelected = i.toString() })
                }else{
                    Text(
                        text = " $i ",
                        fontSize = 35.sp,
                        modifier = Modifier.clickable { numberSelected = i.toString() })
                }

            }
        }

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = {
                reloadGame = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text("Jugar de nuevo")
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                onChangeScreen("selectDifficulty")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text("Cambiar dificultad")
        }
    }
}
