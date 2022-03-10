package com.drimov.pokedexgraphql.data.remote.dto

data class PokemonSpeciesInfo(
    val base_happiness: Int,
    val capture_rate: Int,
    val id: Int,
    val pokemon_v2_growthrate: GrowthRate,
    val pokemon_v2_pokemoncolor: PokemonColor,
    val pokemon_v2_pokemonegggroups: List<PokemonEgggroup>,
    val pokemon_v2_pokemonhabitat: PokemonHabitat,
    val pokemon_v2_pokemons: List<Pokemon>,
    val pokemon_v2_pokemonshape: PokemonShape,
    val pokemon_v2_pokemonspeciesflavortexts: List<FlavorText>,
    val pokemon_v2_pokemonspeciesnames: List<PokemonSpecy>
)