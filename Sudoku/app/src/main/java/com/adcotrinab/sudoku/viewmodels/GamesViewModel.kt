package com.adcotrinab.sudoku.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.adcotrinab.sudoku.DataSource.datasource
import com.adcotrinab.sudoku.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GamesViewModel(application: Application) : AndroidViewModel(application) {

    private val _games = MutableStateFlow(datasource.emptyGameList)
    val games: StateFlow<List<Game>> = _games.asStateFlow()

    /* Lógica de negocio asociada a la clase ******************************************/
    fun setGames(games: List<Game>) {
        _games.value = games
    }



    /**
     * Función auxiliar que devuelve el último id empleado en la lista
     */
    fun getMaxId(list: List<Game>): Int {
        val item = list.sortedByDescending { list -> list.id }.first()
        return item.id
    }

}



