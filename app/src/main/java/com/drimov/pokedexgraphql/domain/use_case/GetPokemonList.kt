package com.drimov.pokedexgraphql.domain.use_case

import com.drimov.pokedexgraphql.domain.model.Generation
import com.drimov.pokedexgraphql.domain.repository.PokemonRepository
import com.drimov.pokedexgraphql.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPokemonList(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(
        generation: String,
        language: String,
        orderBy: String
    ): Flow<Resource<Generation>> {
        return repository.getPokemonList(generation, language, orderBy)
    }
}