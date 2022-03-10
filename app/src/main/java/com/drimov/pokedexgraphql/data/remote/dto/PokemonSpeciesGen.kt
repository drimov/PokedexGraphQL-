package com.drimov.pokedexgraphql.data.remote.dto

data class PokemonSpeciesGen(
    val id: Int,
    val pokemon_v2_pokemoncolor: PokemonColor,
    val pokemon_v2_pokemonspeciesnames: List<PokemonSpecy>
)