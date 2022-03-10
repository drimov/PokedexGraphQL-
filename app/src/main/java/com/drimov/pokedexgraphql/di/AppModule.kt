package com.drimov.pokedexgraphql.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.drimov.pokedexgraphql.data.remote.GraphQLApolloClient
import com.drimov.pokedexgraphql.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
         return ApolloClient.Builder()
             .serverUrl(BASE_URL)
             .okHttpClient(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    @Singleton
    fun provideGraphQLApolloClient(apolloClient: ApolloClient): GraphQLApolloClient {
        return GraphQLApolloClient(apolloClient = apolloClient)
    }

}