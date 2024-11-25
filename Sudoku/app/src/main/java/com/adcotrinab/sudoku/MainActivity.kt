package com.adcotrinab.sudoku

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adcotrinab.sudoku.model.Game
import com.adcotrinab.sudoku.navigation.MainTopBar
import com.adcotrinab.sudoku.navigation.Navigation
import com.adcotrinab.sudoku.ui.theme.ShortDAMGames2024Theme
import com.adcotrinab.sudoku.viewmodels.GamesViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShortDAMGames2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val windowSizeClass = calculateWindowSizeClass(this)
                    ShortDamGamesAppFormat(windowSizeClass, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ShortDamGamesAppFormat(windowSize: WindowSizeClass,modifier: Modifier = Modifier) {
    val navHostController = rememberNavController()
    Scaffold(topBar = { MainTopBar() }) { it ->
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        )
        {
            when (windowSize.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    ShortDamGamesApp(navHostController)
                    //paniaguaa_game(onNavSelected = {})
                }

                WindowWidthSizeClass.Medium -> {
                    Log.d("SIZE", "Aplicación rotada")
                }

                WindowWidthSizeClass.Expanded -> {
                    Log.d("SIZE", "Esto es una tablet")
                }
            }
        }
    }
}

/**
 * Método principal
 *
 * Rellena la lista de juegos con los datos de res/values y a continuación lanza el Scaffold y
 * la navegación.
 */
@Composable
fun ShortDamGamesApp(navHostController: NavHostController) {
    val gamesViewModel: GamesViewModel = viewModel()
    gamesViewModel.setGames(populateGames())
    Navigation(navHostController)
}


/**
 * Función que rellena la lista con los juegos existentes en Datasource
 * Dado que toma recursos de /res no la ponemos en el viewmodel, a pesar de trabajar con la lógica
 * de los datos. El viewmodel no debería acceder nunca a los recursos o al contexto.
 *
 * (igualmente se podría usar AndroidViewModel en lugar de ViewModel)
 */
@Composable
fun populateGames(): List<Game>{
    var games = mutableListOf<Game>()
    val names: Array<String> = stringArrayResource(R.array.gamename_values)
    val authors: Array<String> = stringArrayResource(R.array.gameauthor_values)
    val packages: Array<String> = stringArrayResource(R.array.gamepackage_values)
    val numplayers: Array<String> = stringArrayResource(R.array.gameplayers_values)
    for (i in (0..names.size-1)) {
        val game: Game = Game(0,names.get(i),authors.get(i),packages.get(i),numplayers.get(i).toInt(),1,5)
        games.add(game)
    }
    (0..games.size-1).forEach({ Log.d("SHORTDAM","Lista de juegos: ${games[it].name}")})

    return games
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShortDAMGames2024Theme {
        //ShortDamGamesApp()
    }
}