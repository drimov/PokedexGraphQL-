package com.drimov.pokedexgraphql.data

import com.drimov.PokemonInfoQuery
import com.drimov.PokemonListQuery
import com.drimov.pokedexgraphql.data.local.entity.*
import com.drimov.pokedexgraphql.util.Constants.IMG_EXT
import com.drimov.pokedexgraphql.util.Constants.IMG_URL

fun PokemonListQuery.Data.toEntity() =
    pokemon_v2_generationname.map { generation ->
        GenerationEntity(
            id = generation.generation_id!!,
            pokemonSpecyEntities = generation.pokemon_v2_generation!!.pokemon_v2_pokemonspecies.map {
                PokemonSpeciesEntity(
                    id = it.id,
                    url = IMG_URL.plus(it.id).plus(IMG_EXT),
                    names = it.pokemon_v2_pokemonspeciesnames.map { speciesName ->
                        NamesEntity(
                            name = speciesName.name,
                            genus = null
                        )
                    },
                    baseHappiness = null,
                    captureRate = null,
                    growRate = emptyList(),
                    habitats = emptyList(),
                    eggGroups = emptyList(),
                    pokemons = it.pokemon_v2_pokemons.map { pokemon ->
                        PokemonEntity(
                            types = pokemon.pokemon_v2_pokemontypes.map { type ->
                                TypeEntity(
                                    name = type.pokemon_v2_type!!.name
                                )
                            },
                            stats = emptyList(),
                            abilities = emptyList(),
                            height = null,
                            weight = null
                        )
                    },
                    shapes = emptyList(),
                    flavorTexts = emptyList()
                )
            }
        )
    }.first()

fun PokemonInfoQuery.Data.toEntity() = pokemon_v2_pokemonspecies.map {
    PokemonSpeciesEntity(
        id = it.id,
        url = IMG_URL + it.id + IMG_EXT,
        names = it.pokemon_v2_pokemonspeciesnames.map { speciesName ->
            NamesEntity(
                name = speciesName.name,
                genus = speciesName.genus
            )
        },
        baseHappiness = it.base_happiness,
        captureRate = it.capture_rate,
        growRate = it.pokemon_v2_growthrate!!.pokemon_v2_growthratedescriptions.map { growthRateDesc ->
            GrowthRateEntity(
                description = growthRateDesc.description,
                id = growthRateDesc.language_id!!
            )
        },
        habitats = it.pokemon_v2_pokemonhabitat?.pokemon_v2_pokemonhabitatnames?.map { habitat ->
            HabitatEntity(
                name = habitat.name,
                id = habitat.language_id!!
            )
        },
        eggGroups = it.pokemon_v2_pokemonegggroups.map { eggGroups ->
            EggGroupEntity(
                eggs = eggGroups.pokemon_v2_egggroup!!.pokemon_v2_egggroupnames.map { egg ->
                    EggEntity(
                        name = egg.name
                    )
                }
            )
        },
        pokemons = it.pokemon_v2_pokemons.map { pokemon ->
            PokemonEntity(
                height = pokemon.height,
                weight = pokemon.weight,
                abilities = pokemon.pokemon_v2_pokemonabilities.map { abilities ->
                    AbilitiesEntity(
                        abilities = abilities.pokemon_v2_ability!!.pokemon_v2_abilitynames.map { ability ->
                            AbilityEntity(
                                name = ability.name
                            )
                        }
                    )
                },
                stats = pokemon.pokemon_v2_pokemonstats.map { stats ->
                    StatsEntity(
                        stats = stats.pokemon_v2_stat!!.pokemon_v2_statnames.map { stat ->
                            StatEntity(
                                name = stat.name
                            )
                        },
                        baseStat = stats.base_stat
                    )
                },
                types = pokemon.pokemon_v2_pokemontypes.map { type ->
                    TypeEntity(
                        name = type.pokemon_v2_type!!.name
                    )
                }
            )
        },
        shapes = it.pokemon_v2_pokemonshape?.pokemon_v2_pokemonshapenames?.map { shape ->
            ShapeEntity(
                name = shape.name,
                id = shape.language_id
            )
        },
        flavorTexts = it.pokemon_v2_pokemonspeciesflavortexts.map { flavorText ->
            FlavorTextEntity(
                text = flavorText.flavor_text
            )
        }
    )
}.first()