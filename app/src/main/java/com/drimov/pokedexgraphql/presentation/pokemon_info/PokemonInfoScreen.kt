package com.drimov.pokedexgraphql.presentation.pokemon_info

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PokemonInfoScreen(
    onPopBackStack: () -> Unit,
    viewModel: PokemonInfoViewModel = hiltViewModel()
) {
}