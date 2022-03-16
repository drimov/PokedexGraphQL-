package com.drimov.pokedexgraphql.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.drimov.PokemonInfoQuery
import com.drimov.PokemonListQuery
import com.drimov.type.order_by

class GraphQLApolloClient(
    private val apolloClient: ApolloClient
) {

    suspend fun getPokemonList(
        generation: Int,
        language: Int,
        orderBy: order_by
    ): ApolloResponse<PokemonListQuery.Data> =
        apolloClient.query(
            PokemonListQuery(
                generation = generation,
                language = language,
                order = orderBy,
            )
        ).execute()

    suspend fun getPokemonInfo(
        id: Int,
        language: Int
    ): ApolloResponse<PokemonInfoQuery.Data> =
        apolloClient.query(
            PokemonInfoQuery(
                id = id,
                language = language
            )
        ).execute()
}