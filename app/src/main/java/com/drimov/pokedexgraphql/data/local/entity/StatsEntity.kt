package com.drimov.pokedexgraphql.data.local.entity

data class StatsEntity(
    val stats: List<StatEntity>,
    val baseStat: Int
)
