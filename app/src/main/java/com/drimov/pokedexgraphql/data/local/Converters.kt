package com.drimov.pokedexgraphql.data.local

import androidx.room.TypeConverter
import com.drimov.pokedexgraphql.data.local.entity.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGrowthRate(growthRateEntityList: List<GrowthRateEntity>): String {
        return Gson().toJson(growthRateEntityList)
    }

    @TypeConverter
    fun toGrowthRate(listString: String): List<GrowthRateEntity> {
        val listType = object : TypeToken<List<GrowthRateEntity>>() {}.type
        return Gson().fromJson(listString, listType)
    }

    @TypeConverter
    fun fromEggGroup(EggGroupEntityList: List<EggGroupEntity>): String {
        return Gson().toJson(EggGroupEntityList)
    }

    @TypeConverter
    fun toEggGroup(listString: String): List<EggGroupEntity> {
        val listType = object : TypeToken<List<EggGroupEntity>>() {}.type
        return Gson().fromJson(listString, listType)
    }


    @TypeConverter
    fun fromHabitat(habitatEntityList: List<HabitatEntity>?): String? {
        return Gson().toJson(habitatEntityList)
    }

    @TypeConverter
    fun toHabitat(listString: String?): List<HabitatEntity>? {
        val listType = object : TypeToken<List<HabitatEntity>>() {}.type
        return Gson().fromJson(listString, listType)
    }

    @TypeConverter
    fun fromPokemon(pokemonEntityList: List<PokemonEntity>): String {
        return Gson().toJson(pokemonEntityList)
    }

    @TypeConverter
    fun toPokemon(listString: String): List<PokemonEntity> {
        val listType = object : TypeToken<List<PokemonEntity>>() {}.type
        return Gson().fromJson(listString, listType)
    }


    @TypeConverter
    fun fromShape(shapeEntityList: List<ShapeEntity>?): String {
        return Gson().toJson(shapeEntityList)
    }

    @TypeConverter
    fun toShape(listString: String?): List<ShapeEntity>? {
        val listType = object : TypeToken<List<ShapeEntity>>() {}.type
        return Gson().fromJson(listString, listType)
    }

    @TypeConverter
    fun fromFlavorText(flavorTextEntityList: List<FlavorTextEntity>): String {
        return Gson().toJson(flavorTextEntityList)
    }

    @TypeConverter
    fun toFlavorText(listString: String): List<FlavorTextEntity> {
        val listType = object : TypeToken<List<FlavorTextEntity>>() {}.type
        return Gson().fromJson(listString, listType)
    }

    @TypeConverter
    fun fromNames(namesEntityList: List<NamesEntity>): String {
        return Gson().toJson(namesEntityList)
    }

    @TypeConverter
    fun toNames(listString: String): List<NamesEntity> {
        val listType = object : TypeToken<List<NamesEntity>>() {}.type
        return Gson().fromJson(listString, listType)
    }

    @TypeConverter
    fun fromPokemonSpecies(pokemonSpecieEntityList: List<PokemonSpeciesEntity>): String {
        return Gson().toJson(pokemonSpecieEntityList)
    }

    @TypeConverter
    fun toPokemonSpecies(listString: String): List<PokemonSpeciesEntity> {
        val listType = object : TypeToken<List<PokemonSpeciesEntity>>() {}.type
        return Gson().fromJson(listString, listType)
    }
}