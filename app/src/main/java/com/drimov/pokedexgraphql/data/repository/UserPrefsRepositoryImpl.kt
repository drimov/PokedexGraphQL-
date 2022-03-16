package com.drimov.pokedexgraphql.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.drimov.pokedexgraphql.domain.repository.UserPrefsRepository
import com.drimov.pokedexgraphql.util.Constants
import com.drimov.pokedexgraphql.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException

private const val USER_PREFERENCES = "user_preferences"
private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES)

class UserPrefsRepositoryImpl(
    private val context: Context
) : UserPrefsRepository {

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun putStringPrefs(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override fun getStringPrefs(key: String): Flow<Resource<Int?>> =
        flow {
            emit(Resource.Loading<Int?>())
            try {
                val preferencesKey = intPreferencesKey(key)
                val preferences = context.dataStore.data.first()
                emit(Resource.Success<Int?>(preferences[preferencesKey]))

            } catch (e: IOException) {
                emit(
                    Resource.Error<Int?>(
                        message = Constants.ioExceptionErr
                    )
                )
            }
        }
}