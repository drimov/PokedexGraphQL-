package com.drimov.pokedexgraphql.presentation.pokemon_info

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drimov.pokedexgraphql.domain.use_case.GetPokemonInfo
import com.drimov.pokedexgraphql.util.Resource
import com.drimov.pokedexgraphql.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val getPokemonInfo: GetPokemonInfo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PokemonInfoState())
    val state: State<PokemonInfoState> = _state

    private val _language = mutableStateOf(9)
    val language: State<Int> = _language
    private val _pokemonId = mutableStateOf(0)

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var loadJob: Job? = null

    init {
        val pokemonId = savedStateHandle.get<Int>("id")
        val language = savedStateHandle.get<Int>("language")
        _pokemonId.value = pokemonId!!
        _language.value = language!!
        getPokemonInformation(id = _pokemonId.value, language = _language.value)
    }

    fun onEvent(event: PokemonInfoEvent) {
        when (event) {
            is PokemonInfoEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is PokemonInfoEvent.OnReload -> {
                getPokemonInformation(id = _pokemonId.value, language = event.language)
            }
        }
    }

    private fun getPokemonInformation(id: Int, language: Int) {
        loadJob = viewModelScope.launch {
            getPokemonInfo.invoke(id = id, language = language)
                .collect { pokemon ->
                    when (pokemon) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                pokemonSpecies = pokemon.data,
                                isError = false,
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                pokemonSpecies = pokemon.data,
                                isError = false,
                                isLoading = true
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                pokemonSpecies = pokemon.data,
                                isError = true,
                                isLoading = false,
                                message = pokemon.message
                            )
                        }
                    }
                }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}
