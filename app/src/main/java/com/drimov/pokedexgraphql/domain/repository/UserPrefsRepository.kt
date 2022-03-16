package com.drimov.pokedexgraphql.domain.repository

import com.drimov.pokedexgraphql.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserPrefsRepository {

    suspend fun putStringPrefs(key: String,value: Int)
    fun getStringPrefs(key: String): Flow<Resource<Int?>>
}