package com.drimov.pokedexgraphql.data.local.entity

data class PokemonEntity(
    val height: Int?,
    val weight: Int?,
    val abilities: List<AbilitiesEntity>,
    val stats: List<StatsEntity>,
    val types: List<TypeEntity>
)
