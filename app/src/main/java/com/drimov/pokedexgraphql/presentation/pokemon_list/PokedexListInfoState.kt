package com.drimov.pokedexgraphql.presentation.pokemon_list

import com.drimov.pokedexgraphql.domain.model.PokemonEntry

data class PokedexListInfoState(
    val pokemonItem: List<PokemonEntry> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null
)