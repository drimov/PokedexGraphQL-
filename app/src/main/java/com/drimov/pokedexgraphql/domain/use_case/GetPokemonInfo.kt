package com.drimov.pokedexgraphql.domain.use_case

import com.drimov.pokedexgraphql.domain.model.PokemonSpecies
import com.drimov.pokedexgraphql.domain.repository.PokemonRepository
import com.drimov.pokedexgraphql.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPokemonInfo(
    private val repository: PokemonRepository
) {
    operator fun invoke(id: Int, language: Int): Flow<Resource<PokemonSpecies>> {
        return repository.getPokemonInfo(id, language)
    }
}