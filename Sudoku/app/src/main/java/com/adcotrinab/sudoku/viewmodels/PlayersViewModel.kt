package com.adcotrinab.sudoku.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.adcotrinab.sudoku.model.Player

class PlayersViewModel(application: Application) : AndroidViewModel(application) {
    private val _player1 = MutableStateFlow(Player(1,"Player 1",0,0))
    private val _player2 = MutableStateFlow(Player(2,"Player 2",0,0))
    val player1: StateFlow<Player> = _player1.asStateFlow()
    val player2: StateFlow<Player> = _player2.asStateFlow()

    /* Lógica de negocio asociada a la clase ******************************************/

    /**
     * Actualiza puntuación del jugador indicado 1 o 2.
     */
    fun setScore(id:Int, score:Int){
        if (id==1) _player1.value.score = score else _player2.value.score = score
    }
}



