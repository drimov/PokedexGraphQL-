package com.drimov.pokedexgraphql.data

import com.drimov.PokemonInfoQuery
import com.drimov.PokemonListQuery
import com.drimov.pokedexgraphql.domain.model.*
import com.drimov.pokedexgraphql.util.Constants.IMG_EXT
import com.drimov.pokedexgraphql.util.Constants.IMG_URL


fun PokemonListQuery.Data.toDomainDomain() =
    pokemon_v2_generationname.map { generation ->
        Generation(
            pokemonSpecies = generation.pokemon_v2_generation!!.pokemon_v2_pokemonspecies.map {
                PokemonSpecies(
                    id = it.id,
                    url = IMG_URL.plus(it.id).plus(IMG_EXT),
                    color = it.pokemon_v2_pokemoncolor!!.name,
                    names = it.pokemon_v2_pokemonspeciesnames.map { speciesName ->
                        Names(
                            name = speciesName.name
                        )
                    },
                    baseHappiness = null,
                    captureRate = null,
                    growRate = emptyList(),
                    habitats = emptyList(),
                    eggGroups = emptyList(),
                    pokemons = emptyList(),
                    shapes = emptyList(),
                    flavorTexts = emptyList()
                )
            }
        )
    }.first()

fun PokemonInfoQuery.Data.toDomainModel() = pokemon_v2_pokemonspecies.map {
    PokemonSpecies(
        id = it.id,
        url = IMG_URL + it.id + IMG_EXT,
        color = it.pokemon_v2_pokemoncolor!!.name,
        names = it.pokemon_v2_pokemonspeciesnames.map { speciesName ->
            Names(
                name = speciesName.name
            )
        },
        baseHappiness = it.base_happiness,
        captureRate = it.capture_rate,
        growRate = it.pokemon_v2_growthrate!!.pokemon_v2_growthratedescriptions.map { growthRateDesc ->
            GrowRateDescription(
                description = growthRateDesc.description
            )
        },
        habitats = it.pokemon_v2_pokemonhabitat!!.pokemon_v2_pokemonhabitatnames.map { habitat ->
            Habitat(
                name = habitat.name
            )
        },
        eggGroups = it.pokemon_v2_pokemonegggroups.map { eggGroups ->
            EggGroup(
                eggs = eggGroups.pokemon_v2_egggroup!!.pokemon_v2_egggroupnames.map { egg ->
                    Egg(
                        name = egg.name
                    )
                }
            )
        },
        pokemons = it.pokemon_v2_pokemons.map { pokemon ->
            Pokemon(
                height = pokemon.height!!,
                abilities = pokemon.pokemon_v2_pokemonabilities.map { abilities ->
                    Abilities(
                        abilities = abilities.pokemon_v2_ability!!.pokemon_v2_abilitynames.map { ability ->
                            Ability(
                                name = ability.name
                            )
                        }
                    )
                },
                stats = pokemon.pokemon_v2_pokemonstats.map { stats ->
                    Stats(
                        stats = stats.pokemon_v2_stat!!.pokemon_v2_statnames.map { stat ->
                            Stat(
                                name = stat.name
                            )
                        }
                    )
                },
                types = pokemon.pokemon_v2_pokemontypes.map { type ->
                    Type(
                        name = type.pokemon_v2_type!!.name
                    )
                }
            )
        },
        shapes = it.pokemon_v2_pokemonshape!!.pokemon_v2_pokemonshapenames.map { shape ->
            Shape(
                name = shape.name
            )
        },
        flavorTexts = it.pokemon_v2_pokemonspeciesflavortexts.map { flavorText ->
            FlavorText(
                text = flavorText.flavor_text
            )
        }
    )
}.first()


//fun PokemonListQuery.Data.toDomainDto() = PokemonList(
//    data = Pokedex(
//        pokemon_v2_generationname = pokemon_v2_generationname.map {
//            PokemonGeneration(
//                pokemon_v2_generation = Generation(
//                    pokemon_v2_pokemonspecies = it.pokemon_v2_generation!!.pokemon_v2_pokemonspecies.map { species ->
//                        PokemonSpeciesGen(
//                            id = species.id,
//                            pokemon_v2_pokemoncolor = PokemonColor(
//                                name = species.pokemon_v2_pokemoncolor!!.name
//                            ),
//                            pokemon_v2_pokemonspeciesnames = species.pokemon_v2_pokemonspeciesnames.map { specy ->
//                                PokemonSpecy(
//                                    genus = null,
//                                    name = specy.name
//                                )
//                            }
//                        )
//                    }
//                )
//            )
//        }
//    )
//)

//fun PokemonInfoQuery.Data.toDto() = PokemonInfo(
//    data = PokedexInfoPokemon(
//        pokemon_v2_pokemonspecies = pokemon_v2_pokemonspecies.map {
//
//            PokemonSpeciesInfo(
//                base_happiness = it.base_happiness!!,
//                capture_rate = it.capture_rate!!,
//                id = it.id,
//                pokemon_v2_growthrate = GrowthRate(
//                    pokemon_v2_growthratedescriptions = it.pokemon_v2_growthrate!!.pokemon_v2_growthratedescriptions.map { growRate ->
//                        GrowthRateDescription(
//                            description = growRate.description
//                        )
//                    }
//                ),
//                pokemon_v2_pokemoncolor = PokemonColor(
//                    name = it.pokemon_v2_pokemoncolor!!.name
//                ),
//                pokemon_v2_pokemonegggroups = it.pokemon_v2_pokemonegggroups.map { eggGroup ->
//                    PokemonEgggroup(
//                        pokemon_v2_egggroup = Egggroup(
//                            pokemon_v2_egggroupnames = eggGroup.pokemon_v2_egggroup!!.pokemon_v2_egggroupnames.map { eggGroupName ->
//                                Egg(
//                                    name = eggGroupName.name
//                                )
//                            }
//                        )
//                    )
//                },
//                pokemon_v2_pokemonhabitat = PokemonHabitat(
//                    pokemon_v2_pokemonhabitatnames = it.pokemon_v2_pokemonhabitat!!.pokemon_v2_pokemonhabitatnames.map { habitat ->
//                        Habitat(
//                            name = habitat.name
//                        )
//                    }
//                ),
//                pokemon_v2_pokemons = it.pokemon_v2_pokemons.map { pokemon ->
//                    Pokemon(
//                        height = pokemon.height!!,
//                        pokemon_v2_pokemonabilities = pokemon.pokemon_v2_pokemonabilities.map { abilities ->
//                            PokemonAbility(
//                                pokemon_v2_ability = Abilities(
//                                    pokemon_v2_abilitynames = abilities.pokemon_v2_ability!!.pokemon_v2_abilitynames.map { ability ->
//                                        Ability(
//                                            name = ability.name
//                                        )
//                                    }
//                                )
//                            )
//                        },
//                        pokemon_v2_pokemonstats = pokemon.pokemon_v2_pokemonstats.map { stats ->
//                            PokemonStats(
//                                base_stat = stats.base_stat,
//                                pokemon_v2_stat = Stats(
//                                    pokemon_v2_statnames = stats.pokemon_v2_stat!!.pokemon_v2_statnames.map { stat ->
//                                        StatName(
//                                            name = stat.name
//                                        )
//                                    }
//                                )
//                            )
//                        },
//                        pokemon_v2_pokemontypes = pokemon.pokemon_v2_pokemontypes.map { types ->
//                            PokemonType(
//                                pokemon_v2_type = Type(
//                                    name = types.pokemon_v2_type!!.name
//                                )
//                            )
//                        },
//                        weight = pokemon.weight!!
//                    )
//                },
//                pokemon_v2_pokemonshape = PokemonShape(
//                    pokemon_v2_pokemonshapenames = it.pokemon_v2_pokemonshape!!.pokemon_v2_pokemonshapenames.map { shape ->
//                        Shape(
//                            name = shape.name
//                        )
//                    }
//                ),
//                pokemon_v2_pokemonspeciesflavortexts = it.pokemon_v2_pokemonspeciesflavortexts.map { flavorText ->
//                    FlavorText(
//                        flavor_text = flavorText.flavor_text
//                    )
//                },
//                pokemon_v2_pokemonspeciesnames = it.pokemon_v2_pokemonspeciesnames.map { species ->
//                    PokemonSpecy(
//                        genus = species.genus,
//                        name = species.name
//                    )
//                }
//            )
//        }
//    )
//)