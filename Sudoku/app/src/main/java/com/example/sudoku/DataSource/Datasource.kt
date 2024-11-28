package com.example.sudoku.DataSource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

object datasource {
    // Listas que almacenarán los valores horizontales de los números.
    // Lists that save the horizontal values of numbers.
    val list1: MutableList<Int> = mutableListOf()
    val list2: MutableList<Int> = mutableListOf()
    val list3: MutableList<Int> = mutableListOf()
    val list4: MutableList<Int> = mutableListOf()
    val list5: MutableList<Int> = mutableListOf()
    val list6: MutableList<Int> = mutableListOf()
    val list7: MutableList<Int> = mutableListOf()
    val list8: MutableList<Int> = mutableListOf()
    val list9: MutableList<Int> = mutableListOf()

    // Lista que almacenará todas las listas.
    // List that saves all lists.
    val lists: MutableList<MutableList<Int>> =
        mutableStateListOf(list1, list2, list3, list4, list5, list6, list7, list8, list9)

    val myMap: MutableMap<String,String> = emptyMap<String,String>().toMutableMap()
    var difficulty = ""
    var numberSelected = ""
    var score by mutableIntStateOf(0)
    var failures by mutableIntStateOf(0)
    var hiddenPositions: List<Pair<Int,Int>> = listOf()

    // En un sudoku los números no se pueden repetir en la misma horizontal, vertical ni en el cuadrante de 3x3.
    // In a sudoku the numbers cannot be repeated in the same horizontal, vertical or 3x3 quadrant.
    fun checkExistence(lists: List<MutableList<Int>>, row: Int, column: Int, number: Int): Boolean {

        // Primero verificamos que no exista en la horizontal.
        // First of all we check if it exists in the horizontal.
        if (lists[row].contains(number)){
            return true
        }

        // Luego verificamos que no exista en la vertical.
        // After that we check if it exists in the vertical.
        for (lista in lists) {
            if (lista[column] == number) {
                return true
            }
        }

        // Y por último verificamos el cuadrante.
        // Finally we check the quadrant.
        val startRow = row / 3 * 3
        val startCol = column / 3 * 3
        for (i in startRow until startRow + 3) {
            for (j in startCol until startCol + 3) {
                if (lists[i][j] == number) return true
            }
        }

        return false
    }

    // Generar un tablero de Sudoku.
    // Generate a sudoku grid.
    fun generateSudoku(myLists: MutableList<MutableList<Int>>): Boolean {
        for (list in myLists) {

            for (column in 0 until 9) {
                // Si el valor de la lista es 0, se genera un número aleatorio.
                // If the value in the list is 0, it generates a random number.
                if (list[column] == 0) {
                    // Lista con números del 1 al 9 desordenados.
                    // List with shuffled numbers from 1 to 9.
                    val numbers = (1..9).shuffled()

                    // Comprobar si el número generado existe en la lista.
                    // Check if the generated number exists in the list.
                    for (number in numbers) {
                        // Si no existe se añade.
                        // If it doesn't exists, it is added.
                        if (!checkExistence(myLists, myLists.indexOf(list), column, number)) {
                            list[column] = number
                            // Se usa la recursividad para seguir rellenando el tablero.
                            // It uses recursion to continue filling the grid.
                            if (generateSudoku(myLists)) return true
                            // Si no devuelve true es porque ese número aleatorio no es válido.
                            // Entonces se sale del bucle actual y vuelve a generar otra lista con números desordenados.
                            // If it doesn't return true it is because that random number is not valid.
                            // In that case, it exists the actual loop and generates another list with shuffled numbers.
                            list[column] = 0
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    fun generateHiddenPositions(amount: Int): List<Pair<Int, Int>> {
        val positions = mutableSetOf<Pair<Int, Int>>()
        while (positions.size < amount) {
            val row = Random.nextInt(0, 9)
            val column = Random.nextInt(0, 9)
            positions.add(Pair(row, column))
        }
        return positions.toList()
    }

    fun generateGrid() {
        // Se crea el tablero vacío.
        // It creates an empty grid.
        val grid: MutableList<MutableList<Int>> = mutableStateListOf<MutableList<Int>>()

        for(i in 0 until 9){
            val gridRow: MutableList<Int> = mutableListOf()
            for(j in 0 until 9){
                gridRow.add(0)
            }
            grid.add(gridRow)
        }

        // Se genera el tablero.
        // It generates the grid.
        generateSudoku(grid)

        // Asignar los valores generados a las listas individuales.
        // Assigns the generated values to the individual lists.
        for (list in lists) {
            list.clear()
            list.addAll(grid[lists.indexOf(list)])
        }

        hiddenPositions = when (difficulty) {
            "easy" -> generateHiddenPositions(10)
            "medium" -> generateHiddenPositions(30)
            "difficult" -> generateHiddenPositions(60)
            else -> listOf()
        }
    }
}
