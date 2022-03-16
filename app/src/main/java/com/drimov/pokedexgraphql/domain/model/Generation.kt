package com.drimov.pokedexgraphql.domain.model

data class Generation(
    val id: Int,
    val pokemonSpecies: List<PokemonSpecies>
)