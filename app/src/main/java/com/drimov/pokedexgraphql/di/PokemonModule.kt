package com.drimov.pokedexgraphql.di

import com.drimov.pokedexgraphql.data.local.PokedexDatabase
import com.drimov.pokedexgraphql.data.remote.GraphQLApolloClient
import com.drimov.pokedexgraphql.data.repository.PokemonRepositoryImpl
import com.drimov.pokedexgraphql.domain.repository.PokemonRepository
import com.drimov.pokedexgraphql.domain.use_case.GetPokemonInfo
import com.drimov.pokedexgraphql.domain.use_case.GetPokemonList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: GraphQLApolloClient,
        database: PokedexDatabase
    ): PokemonRepository {
        return PokemonRepositoryImpl(api, database.pokemonDao)
    }

    @Provides
    @Singleton
    fun provideGetPokemonList(repository: PokemonRepository): GetPokemonList {
        return GetPokemonList(repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetPokemonInfo(repository: PokemonRepository): GetPokemonInfo {
        return GetPokemonInfo(repository = repository)
    }
}