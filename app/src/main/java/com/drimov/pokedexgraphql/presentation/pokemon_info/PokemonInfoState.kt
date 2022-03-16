package com.drimov.pokedexgraphql.presentation.pokemon_info

import com.drimov.pokedexgraphql.domain.model.PokemonSpecies

data class PokemonInfoState(
    val pokemonSpecies: PokemonSpecies? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null
)