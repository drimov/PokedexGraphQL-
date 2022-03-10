package com.drimov.pokedexgraphql.presentation.pokemon_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drimov.pokedexgraphql.domain.model.PokemonEntry
import com.drimov.pokedexgraphql.domain.use_case.GetPokemonList
import com.drimov.pokedexgraphql.domain.use_case.GetStringPrefs
import com.drimov.pokedexgraphql.domain.use_case.PutStringPrefs
import com.drimov.pokedexgraphql.util.Constants.KEY_STORE
import com.drimov.pokedexgraphql.util.Resource
import com.drimov.pokedexgraphql.util.Routes
import com.drimov.pokedexgraphql.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList,
    private val getStringPrefs: GetStringPrefs,
    private val putStringPrefs: PutStringPrefs
) : ViewModel() {

    private val _state = mutableStateOf(PokedexListInfoState())
    val state: State<PokedexListInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _language = MutableStateFlow("en")
    val language = _language.asStateFlow()

    private val _generation = MutableStateFlow("generation-i")


    init {
        getLanguage(language.value)
        onLoadPokemonList(_generation.value, language.value)
    }

    private var loadJob: Job? = null

    fun onEvent(event: PokedexListEvent) {
        when (event) {
            is PokedexListEvent.OnPokemonClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.POKEMON_INFO + "?id=${event.id}?language=${event.language}"))
            }
            is PokedexListEvent.OnGenerationClick -> {
                onLoadPokemonList(event.gen, language.value)
            }
            is PokedexListEvent.OnLanguageClick -> {
                if(event.language != language.value){
                    setLanguage(event.language)
                    _language.value = event.language
                    onLoadPokemonList(_generation.value, language.value)
                }
            }

            is PokedexListEvent.OnReload -> {
                if (event.gen.isNullOrEmpty()) onLoadPokemonList(_generation.value, language.value)
                else {
                    onLoadPokemonList(event.gen, language.value)
                    _generation.value = event.gen
                }
            }
        }
    }

    private fun setLanguage(language: String) {
        loadJob = viewModelScope.launch {
            putStringPrefs.invoke(KEY_STORE, language)
        }
    }

    private fun getLanguage(defaultLanguage: String) {

        loadJob = viewModelScope.launch {
            getStringPrefs.invoke(KEY_STORE).collect { entryLanguage ->
                when (entryLanguage) {
                    is Resource.Success -> {
                        if (entryLanguage.data.isNullOrEmpty()) {
                            setLanguage(defaultLanguage)
                        } else {
                            _language.value = entryLanguage.data
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun onLoadPokemonList(generation: String, languageSelected: String) {
        loadJob = viewModelScope.launch {
            getPokemonList.invoke(
                generation = generation,
                language = languageSelected,
                orderBy = "asc"
            )
                .onEach { item ->
                    when (item) {
                        is Resource.Success -> {
                            var pokedexListEntry: List<PokemonEntry> = emptyList()
                            pokedexListEntry = item.data!!.pokemonSpecies.map {

                                PokemonEntry(
                                    id = it.id,
                                    name = it.names[0].name,
                                    color = it.color,
                                    url = it.url
                                )
                            }
                            _state.value = state.value.copy(
                                pokemonItem = pokedexListEntry,
                                isLoading = false,
                                isError = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                pokemonItem = emptyList(),
                                isLoading = true,
                                isError = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                pokemonItem = emptyList(),
                                isLoading = false,
                                isError = true,
                                message = item.message
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

}
