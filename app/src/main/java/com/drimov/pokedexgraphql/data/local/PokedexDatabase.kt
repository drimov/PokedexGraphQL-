package com.drimov.pokedexgraphql.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.drimov.pokedexgraphql.data.local.dao.PokemonDao
import com.drimov.pokedexgraphql.data.local.entity.GenerationEntity
import com.drimov.pokedexgraphql.data.local.entity.PokemonSpeciesEntity

@Database(
    entities = [PokemonSpeciesEntity::class,GenerationEntity::class],
    version = 4,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class PokedexDatabase : RoomDatabase() {

    abstract val pokemonDao: PokemonDao

    companion object {
        const val DB_NAME = "pokedex_db"
    }
}