package com.drimov.pokedexgraphql.data.repository

import com.apollographql.apollo3.exception.ApolloException
import com.drimov.pokedexgraphql.data.local.dao.PokemonDao
import com.drimov.pokedexgraphql.data.local.entity.GenerationEntity
import com.drimov.pokedexgraphql.data.local.entity.PokemonSpeciesEntity
import com.drimov.pokedexgraphql.data.remote.GraphQLApolloClient
import com.drimov.pokedexgraphql.data.toEntity
import com.drimov.pokedexgraphql.domain.model.Generation
import com.drimov.pokedexgraphql.domain.model.PokemonSpecies
import com.drimov.pokedexgraphql.domain.repository.PokemonRepository
import com.drimov.pokedexgraphql.util.Constants
import com.drimov.pokedexgraphql.util.Resource
import com.drimov.type.order_by
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PokemonRepositoryImpl(
    private val api: GraphQLApolloClient,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override fun getPokemonList(
        generation: Int,
        language: Int,
        orderBy: String
    ): Flow<Resource<Generation>> = flow {
        emit(Resource.Loading())

        try {
            val result = api.getPokemonList(
                generation = generation,
                language = language,
                orderBy = order_by.valueOf(orderBy)
            )

            val genPokemon: GenerationEntity = result.data!!.toEntity()
            pokemonDao.insertGeneration(genPokemon)
            val genList = pokemonDao.getPokemonGen(generation).toDomainModel()
            emit(Resource.Success(genList))

        } catch (e: ApolloException) {
            emit(Resource.Error<Generation>(message = Constants.httpExceptionErr))
        }

    }

    override fun getPokemonInfo(id: Int, language: Int): Flow<Resource<PokemonSpecies>> =
        flow {

            emit(Resource.Loading<PokemonSpecies>())

            try {
                val result = api.getPokemonInfo(
                    id = id,
                    language = language
                )
                val pokemon: PokemonSpeciesEntity = result.data!!.toEntity()
                pokemonDao.insertPokemon(pokemon)
                val pokemoInfo = pokemonDao.getPokemon(id).toDomainModel()
                emit(Resource.Success(pokemoInfo))

            } catch (e: ApolloException) {
                emit(Resource.Error<PokemonSpecies>(message = Constants.httpExceptionErr))
            }
        }
}