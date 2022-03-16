package com.drimov.pokedexgraphql.domain.model

data class Stats(
    val stats: List<Stat>,
    val baseStat: Int
)
