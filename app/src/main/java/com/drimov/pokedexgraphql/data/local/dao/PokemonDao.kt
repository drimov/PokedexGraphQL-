package com.drimov.pokedexgraphql.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drimov.pokedexgraphql.data.local.entity.GenerationEntity
import com.drimov.pokedexgraphql.data.local.entity.PokemonSpeciesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneration(generationEntity: GenerationEntity)

    @Query("SELECT * FROM generationentity WHERE id=:key")
    suspend fun getPokemonGen(key: Int): GenerationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonspeciesentity: PokemonSpeciesEntity)

    @Query("SELECT * FROM pokemonspeciesentity where id=:key")
    suspend fun getPokemon(key: Int): PokemonSpeciesEntity
}