package com.drimov.pokedexgraphql.domain.model

data class Pokemon(
    val height: Int?,
    val weight: Int?,
    val abilities: List<Abilities>,
    val stats: List<Stats>,
    val types: List<Type>
)
