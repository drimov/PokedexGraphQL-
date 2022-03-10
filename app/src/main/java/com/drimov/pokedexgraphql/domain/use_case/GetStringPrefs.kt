package com.drimov.pokedexgraphql.domain.use_case

import com.drimov.pokedexgraphql.domain.repository.UserPrefsRepository
import com.drimov.pokedexgraphql.util.Resource
import kotlinx.coroutines.flow.Flow

class GetStringPrefs(
    private val repository: UserPrefsRepository
) {
    suspend operator fun invoke(key:String): Flow<Resource<String?>> {
        return repository.getStringPrefs(key)
    }
}