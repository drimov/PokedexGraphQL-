package com.drimov.pokedexgraphql.domain.model

data class PokemonSpecies(
    val id: Int,
    val url: String,
    val baseHappiness: Int?,
    val captureRate: Int?,
    val growRate: List<GrowRateDescription>,
    val color: String,
    val eggGroups: List<EggGroup>,
    val habitats: List<Habitat>,
    val pokemons: List<Pokemon>,
    val shapes: List<Shape>,
    val flavorTexts: List<FlavorText>,
    val names: List<Names>
)