package com.drimov.pokedexgraphql.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drimov.pokedexgraphql.domain.model.*

@Entity
data class GenerationEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val pokemonSpecyEntities: List<PokemonSpeciesEntity>
) {
    fun toDomainModel(): Generation {
        return Generation(
            id = id,
            pokemonSpecies = pokemonSpecyEntities.map { pSEntity ->
                pSEntity.toDomainModel()
            }
        )
    }
}