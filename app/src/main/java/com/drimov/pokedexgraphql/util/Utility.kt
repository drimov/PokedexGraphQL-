package com.drimov.pokedexgraphql.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.drimov.pokedexgraphql.domain.model.Stat
import com.drimov.pokedexgraphql.domain.model.Stats
import com.drimov.pokedexgraphql.domain.model.Type
import com.drimov.pokedexgraphql.presentation.ui.theme.*
import java.util.*


fun typeToColor(type: Type): Color {
    return when (type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.White
    }
}

fun statToColor(index: Int): Color {
    return when (index % 2) {
        0 -> Color.White
        else -> Blue700
    }
}

fun calWithRef(number: Double, ref: Double): String {
    return String.format(Locale.ROOT, "%.2f", number / ref)
}

fun numberToLanguage(number: Int): String {
    return when (number) {
        1 -> "ja"
        3 -> "ko"
        5 -> "fr"
        7 -> "es"
        8 -> "it"
        else -> "en"
    }
}

fun numberToIndex(number: Int): String {
    return when (number) {
        in 0..9 -> "#00${number}"
        in 10..99 -> "#0${number}"
        else -> "#$number"
    }
}

fun nbToRomanNb(number: Int): String {
    return when (number) {
        in 1..3 -> "I".repeat(number)
        4 -> "IV"
        in 5..8 -> "V".plus("I".repeat(number - 5))
        else -> "Error"
    }
}

fun parseGeneration(gen: String): Int {
    val genR = 0
    val length = gen.length

    return when (gen.subSequence(startIndex = 0, endIndex = 1)) {
        "I" -> when (length) {
            1 ->  genR.plus(length)
            in 2..3 -> when(gen.subSequence(startIndex = 1, endIndex = 2)){
                "I" ->  genR.plus(length)
                else-> genR.plus(4)
            }
            else -> 1
        }
        "V" -> genR.plus(5).plus(length-1)
        else -> 1
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun rightTranslate(listName: MutableMap<Int, String>, language: Int): String? {
    var result: String? = ""

    listName.map { entry ->
        if (result.isNullOrEmpty()) {
            when (entry.key) {
                language -> result = entry.value
                else -> when (entry.key) {
                    9 -> result = entry.value
                }
            }
        }
    }
    return result
}