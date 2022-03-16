package com.drimov.pokedexgraphql.domain.use_case

import com.drimov.pokedexgraphql.domain.repository.UserPrefsRepository
import com.drimov.pokedexgraphql.util.Resource
import kotlinx.coroutines.flow.Flow

class GetStringPrefs(
    private val repository: UserPrefsRepository
) {
    operator fun invoke(key:String): Flow<Resource<Int?>> {
        return repository.getStringPrefs(key)
    }
}