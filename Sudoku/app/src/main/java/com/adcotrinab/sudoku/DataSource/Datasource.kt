package com.adcotrinab.sudoku.DataSource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import com.adcotrinab.sudoku.model.Game
import kotlin.random.Random

object datasource {
    val emptyGameList = listOf(
        Game(0,"","","",0,0,0)
    )
    val emptyGame = Game(0,"","","",0,0,0)

    // Listas que almacenarán los valores horizontales de los números
    val lista1: MutableList<Int> = mutableListOf()
    val lista2: MutableList<Int> = mutableListOf()
    val lista3: MutableList<Int> = mutableListOf()
    val lista4: MutableList<Int> = mutableListOf()
    val lista5: MutableList<Int> = mutableListOf()
    val lista6: MutableList<Int> = mutableListOf()
    val lista7: MutableList<Int> = mutableListOf()
    val lista8: MutableList<Int> = mutableListOf()
    val lista9: MutableList<Int> = mutableListOf()

    // Lista que almacenará todas las listas
    val listas: MutableList<MutableList<Int>> =
        mutableStateListOf(lista1, lista2, lista3, lista4, lista5, lista6, lista7, lista8, lista9)

    val miMapa: MutableMap<String,String> = emptyMap<String,String>().toMutableMap()
    var dificultad = ""
    var numberSelected = ""
    var puntuacion by mutableIntStateOf(0)
    var fallos by mutableIntStateOf(0)
    var posicionesOcultas: List<Pair<Int,Int>> = listOf()

    // En un sudoku los números no se pueden repetir en la misma horizontal, vertical ni en el cuadrante de 3x3.
    fun comprobarExistencia(listas: List<MutableList<Int>>, fila: Int, col: Int, num: Int): Boolean {
        // Primero verificamos que no exista en la horizontal
        if (listas[fila].contains(num)){
            return true
        }

        // Luego verificamos que no exista en la vertical
        for (lista in listas) {
            if (lista[col] == num) {
                return true
            }
        }

        // Y por último verificamos el cuadrante.
        val startRow = fila / 3 * 3
        val startCol = col / 3 * 3
        for (i in startRow until startRow + 3) {
            for (j in startCol until startCol + 3) {
                if (listas[i][j] == num) return true
            }
        }

        return false
    }

    // Generar un tablero de Sudoku
    fun generateSudoku(listas: MutableList<MutableList<Int>>): Boolean {
        for (lista in listas) {
            for (col in 0 until 9) { // Se recorren las listas de números

                if (lista[col] == 0) { // Si el valor de la lista es 0, se genera un número aleatorio

                    val numbers = (1..9).shuffled() // Lista con números del 1 al 9 desordenados

                    // Comprobar si el número generado existe en la lista
                    for (num in numbers) {
                        if (!comprobarExistencia(listas, listas.indexOf(lista), col, num)) {  // Si no existe se añade
                            lista[col] = num
                            // Se usa la recursividad para seguir rellenando el tablero
                            if (generateSudoku(listas)) return true
                            // Si no devuelve true es porque ese número aleatorio no es válido.
                            // Entonces se sale del bucle actual y vuelve a generar otra lista de números desordenados
                            lista[col] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    fun generarPosicionesOcultas(cantidad: Int): List<Pair<Int, Int>> {
        val posiciones = mutableSetOf<Pair<Int, Int>>()
        while (posiciones.size < cantidad) {
            val fila = Random.nextInt(0, 9)
            val columna = Random.nextInt(0, 9)
            posiciones.add(Pair(fila, columna))
        }
        return posiciones.toList()
    }

    fun generateGrid() {
        // Se crea el tablero vacío
        val grid: MutableList<MutableList<Int>> = mutableStateListOf<MutableList<Int>>()

        for(i in 0 until 9){
            val gridRow: MutableList<Int> = mutableListOf()
            for(j in 0 until 9){
                gridRow.add(0)
            }
            grid.add(gridRow)
        }

        // Se genera el tablero
        generateSudoku(grid)

        // Asignar los valores generados a las listas individuales
        for (lista in listas) {
            lista.clear()
            lista.addAll(grid[listas.indexOf(lista)])
        }

        posicionesOcultas = when (dificultad) {
            "facil" -> generarPosicionesOcultas(10)
            "medio" -> generarPosicionesOcultas(30)
            "dificil" -> generarPosicionesOcultas(60)
            else -> listOf()
        }
    }

    fun comprobar(): Boolean {
        var complete: Boolean = true
        for (i in miMapa.values) {
            if (i == "") {
                complete = false
            }
        }
        return complete
    }
}
