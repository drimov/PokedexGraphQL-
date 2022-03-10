package com.drimov.pokedexgraphql.domain.use_case

import com.drimov.pokedexgraphql.domain.repository.UserPrefsRepository

class PutStringPrefs(
    private val repository: UserPrefsRepository
) {
    suspend operator fun invoke(key: String, value: String) {
        repository.putStringPrefs(key, value)
    }
}