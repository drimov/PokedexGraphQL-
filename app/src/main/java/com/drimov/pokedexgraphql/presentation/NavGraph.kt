package com.drimov.pokedexgraphql.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.drimov.pokedexgraphql.presentation.pokemon_list.PokemonListScreen
import com.drimov.pokedexgraphql.presentation.pokemon_info.PokemonInfoScreen
import com.drimov.pokedexgraphql.presentation.splash.SplashScreen
import com.drimov.pokedexgraphql.util.Routes

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(route = Routes.SPLASH) {
            SplashScreen(onNavigate = { navController.navigate(it.route) })
        }
        composable(route = Routes.POKEMON_LIST) {
            PokemonListScreen(onNavigate = { navController.navigate(it.route) })
        }
        composable(
            route = Routes.POKEMON_INFO + "?id={id}?language={language}",
            arguments = listOf(

                navArgument(name = "id") {
                    type = NavType.IntType
                },
                navArgument(name = "language") {
                    type = NavType.IntType
                }
            )
        ) {
            PokemonInfoScreen(onPopBackStack = { navController.popBackStack() })
        }
    }
}