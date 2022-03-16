package com.drimov.pokedexgraphql.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drimov.pokedexgraphql.domain.model.*

@Entity
data class PokemonSpeciesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val url: String,
    val baseHappiness: Int?,
    val captureRate: Int?,
    val growRate: List<GrowthRateEntity>,
    val eggGroups: List<EggGroupEntity>,
    val habitats: List<HabitatEntity>?,
    val pokemons: List<PokemonEntity>,
    val shapes: List<ShapeEntity?>?,
    val flavorTexts: List<FlavorTextEntity>,
    val names: List<NamesEntity>
) {
    fun toDomainModel(): PokemonSpecies {
        return PokemonSpecies(
            id = id,
            url = url,
            baseHappiness = baseHappiness,
            captureRate = captureRate,
            growthRate = growRate.map { growth ->
                GrowthRate(
                    description = growth.description,
                    id = growth.id
                )
            },
            eggGroups = eggGroups.map { eggGroup ->
                EggGroup(
                    eggs = eggGroup.eggs.map { egg ->
                        Egg(
                            name = egg.name
                        )
                    }
                )
            },
            habitats = habitats?.map { habitat ->
                Habitat(
                    name = habitat.name,
                    id = habitat.id
                )
            },
            pokemons = pokemons.map { pokemon ->
                Pokemon(
                    height = pokemon.height,
                    weight = pokemon.weight,
                    abilities = pokemon.abilities.map { abilities ->
                        Abilities(
                            abilities = abilities.abilities.map { ability ->
                                Ability(
                                    name = ability.name
                                )
                            }
                        )
                    },
                    stats = pokemon.stats.map { stats ->
                        Stats(
                            stats = stats.stats.map { stat ->
                                Stat(
                                    name = stat.name
                                )
                            },
                            baseStat = stats.baseStat
                        )
                    },
                    types = pokemon.types.map { type ->
                        Type(
                            name = type.name
                        )
                    }
                )
            },
            shapes = shapes?.map { shape ->
                Shape(
                    name = shape?.name,
                    id = shape?.id
                )
            },
            flavorTexts = flavorTexts.map { flavorText ->
                FlavorText(
                    text = flavorText.text
                )
            },
            names = names.map { names ->
                Names(
                    name = names.name,
                    genus = names.genus
                )
            }
        )
    }
}