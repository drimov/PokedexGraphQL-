package com.drimov.pokedexgraphql.data.remote.dto

data class Pokemon(
    val height: Int,
    val pokemon_v2_pokemonabilities: List<PokemonAbility>,
    val pokemon_v2_pokemonstats: List<PokemonStats>,
    val pokemon_v2_pokemontypes: List<PokemonType>,
    val weight: Int
)