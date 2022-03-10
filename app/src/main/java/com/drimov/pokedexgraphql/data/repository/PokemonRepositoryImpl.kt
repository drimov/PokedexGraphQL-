package com.drimov.pokedexgraphql.data.repository

import com.drimov.pokedexgraphql.data.remote.GraphQLApolloClient
import com.drimov.pokedexgraphql.data.toDomainDomain
import com.drimov.pokedexgraphql.data.toDomainModel
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
    private val api: GraphQLApolloClient
) : PokemonRepository {

    override suspend fun getPokemonList(
        generation: String,
        language: String,
        orderBy: String
    ): Flow<Resource<Generation>> = flow {
        emit(Resource.Loading())

        try {
            val result = api.getPokemonList(
                generation = generation,
                language = language,
                orderBy = order_by.valueOf(orderBy)
            )

            val listPokemon: Generation = result.data!!.toDomainDomain()
            emit(Resource.Success(listPokemon))

        } catch (e: HttpException) {
            emit(Resource.Error<Generation>(message = Constants.httpExceptionErr))
        }

    }

    override suspend fun getPokemonInfo(id: Int, language: String): Flow<Resource<PokemonSpecies>> =
        flow {

            emit(Resource.Loading<PokemonSpecies>())

            try {
                val result = api.getPokemonInfo(
                    id = id,
                    language = language
                )
                val pokemonInfo: PokemonSpecies = result.data!!.toDomainModel()
                emit(Resource.Success(pokemonInfo))

            } catch (e: HttpException) {
                emit(Resource.Error<PokemonSpecies>(message = Constants.httpExceptionErr))
            }
        }
}