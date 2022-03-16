package com.drimov.pokedexgraphql.presentation.pokemon_info

sealed class PokemonInfoEvent {
    object OnBackPressed: PokemonInfoEvent()
    data class OnReload(val language: Int): PokemonInfoEvent()
}