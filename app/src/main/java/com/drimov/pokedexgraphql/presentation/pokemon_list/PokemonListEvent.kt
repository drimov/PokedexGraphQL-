package com.drimov.pokedexgraphql.presentation.pokemon_list

sealed class PokemonListEvent {
    data class OnPokemonClick(val id: Int,val language: Int) : PokemonListEvent()
    data class OnGenerationClick(val gen: Int) : PokemonListEvent()
    data class OnReload(val gen: Int?) : PokemonListEvent()
    data class OnLanguageClick(val language: Int) : PokemonListEvent()
}