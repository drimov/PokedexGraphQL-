package com.drimov.pokedexgraphql.presentation.pokemon_list

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

    private val _language = MutableStateFlow(9)
    val language = _language.asStateFlow()

    private val _generation = MutableStateFlow(1)
    val generation = _generation.asStateFlow()


    init {
        getLanguage(language.value)
    }

    private var loadJob: Job? = null

    fun onEvent(event: PokemonListEvent) {
        when (event) {
            is PokemonListEvent.OnPokemonClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.POKEMON_INFO + "?id=${event.id}?language=${event.language}"))
            }
            is PokemonListEvent.OnGenerationClick -> {
                onLoadPokemonList(event.gen, _language.value)
            }
            is PokemonListEvent.OnLanguageClick -> {
                if (event.language != language.value) {
                    setLanguage(event.language)
                    _language.value = event.language
                    onLoadPokemonList(_generation.value, _language.value)
                }
            }

            is PokemonListEvent.OnReload -> {
                if (event.gen == null) onLoadPokemonList(_generation.value, language.value)
                else {
                    onLoadPokemonList(event.gen, language.value)
                    _generation.value = event.gen
                }
            }
        }
    }

    private fun setLanguage(language: Int) {
        loadJob = viewModelScope.launch {
            putStringPrefs.invoke(KEY_STORE, language)
        }
    }

    private fun getLanguage(defaultLanguage: Int) {

        loadJob = viewModelScope.launch {
            getStringPrefs.invoke(KEY_STORE).collect { entryLanguage ->
                when (entryLanguage) {
                    is Resource.Success -> {
                        if (entryLanguage.data == null) {
                            setLanguage(defaultLanguage)
                        } else {
                            _language.value = entryLanguage.data
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                }
            }
            onLoadPokemonList(_generation.value, language.value)
        }
    }

    private fun onLoadPokemonList(generation: Int, languageSelected: Int) {
        loadJob = viewModelScope.launch {
            getPokemonList.invoke(
                generation = generation,
                language = languageSelected,
                orderBy = "asc"
            ).collect { item ->
                when (item) {
                    is Resource.Success -> {
                        var pokedexListEntry: List<PokemonEntry> = emptyList()
                        pokedexListEntry = item.data!!.pokemonSpecies.map {

                            PokemonEntry(
                                id = it.id,
                                name = it.names[0].name,
                                color = it.pokemons[0].types[0],
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
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

}
