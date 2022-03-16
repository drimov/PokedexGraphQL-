package com.drimov.pokedexgraphql.util

import com.drimov.pokedexgraphql.util.Constants.typeUrlImgP1
import com.drimov.pokedexgraphql.util.Constants.typeUrlImgP2
import java.util.*

fun String.ucFirst(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

fun String.urlType(): String {
    return typeUrlImgP1.plus(this).plus(typeUrlImgP2)
}