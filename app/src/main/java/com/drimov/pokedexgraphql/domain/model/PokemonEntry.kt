package com.drimov.pokedexgraphql.domain.model

data class PokemonEntry(
    val name: String,
    val url: String,
    val id: Int,
    val color: Type
//    val color: String
)