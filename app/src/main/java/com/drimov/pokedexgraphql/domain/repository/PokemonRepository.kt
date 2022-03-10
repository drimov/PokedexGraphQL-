package com.drimov.pokedexgraphql.domain.repository

import com.drimov.pokedexgraphql.data.remote.dto.PokemonInfo
import com.drimov.pokedexgraphql.data.remote.dto.PokemonList
import com.drimov.pokedexgraphql.domain.model.Generation
import com.drimov.pokedexgraphql.domain.model.PokemonSpecies
import com.drimov.pokedexgraphql.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(generation: String,language:String,orderBy: String): Flow<Resource<Generation>>
    suspend fun getPokemonInfo(id: Int, language: String): Flow<Resource<PokemonSpecies>>
}