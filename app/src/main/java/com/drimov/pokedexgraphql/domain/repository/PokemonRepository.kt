package com.drimov.pokedexgraphql.domain.repository

import com.drimov.pokedexgraphql.domain.model.Generation
import com.drimov.pokedexgraphql.domain.model.PokemonSpecies
import com.drimov.pokedexgraphql.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(
        generation: Int,
        language: Int,
        orderBy: String
    ): Flow<Resource<Generation>>

    fun getPokemonInfo(id: Int, language: Int): Flow<Resource<PokemonSpecies>>
}