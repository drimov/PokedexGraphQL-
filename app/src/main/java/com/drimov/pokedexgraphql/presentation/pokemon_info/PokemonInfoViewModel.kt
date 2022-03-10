package com.drimov.pokedexgraphql.presentation.pokemon_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

}