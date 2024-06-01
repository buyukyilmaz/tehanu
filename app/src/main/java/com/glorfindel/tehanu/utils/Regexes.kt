package com.glorfindel.tehanu.utils

import android.util.Patterns

object Regexes {
    private const val TURKISH_CHARACTERS = "ğşçıüöĞŞÇİÖÜ"
    private const val ENGLISH_CHARACTERS = "gsciuoGSCIOU"

    val letter by lazy { Regex("[a-zA-Z]") }
    val letterTurkish by lazy { Regex("[a-zA-Z$TURKISH_CHARACTERS]") }
    val nonLetter by lazy { Regex("[^a-zA-Z]") }
    val nonLetterTurkish by lazy { Regex("[^a-zA-Z$TURKISH_CHARACTERS]") }
    val alphanumeric by lazy { Regex("[A-Za-z0-9]") }
    val alphanumericTurkish by lazy { Regex("[A-Za-z0-9$TURKISH_CHARACTERS]") }
    val nonAlphanumeric by lazy { Regex("[^A-Za-z0-9]") }
    val nonAlphanumericTurkish by lazy { Regex("[^A-Za-z0-9$TURKISH_CHARACTERS]") }
    val numeric by lazy { Regex("[0-9]") }
    val nonNumeric by lazy { Regex("[^0-9]") }
    val email by lazy { Patterns.EMAIL_ADDRESS.toRegex() }

    data object Iban {
        val withPrefixWithSpace by lazy { Regex("TR\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{2}") }
        val withPrefixWithoutSpace by lazy { Regex("TR\\d{24}") }
        val withoutPrefixWithSpace by lazy { Regex("\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{2}") }
        val withoutPrefixWithoutSpace by lazy { Regex("\\d{24}") }
    }

    data object CardNo {
        val withoutSpace by lazy { Regex("\\d{16}") }
        val withSpace by lazy { Regex("\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}") }
    }

    data object Date {
        val withDot by lazy { Regex("\\d{2}\\.\\d{2}\\.\\d{4}") }
        val withSlash by lazy { Regex("\\d{2}/\\d{2}/\\d{4}") }
    }

    data object Gsm {
        val withSpace by lazy { Regex("\\d{3}\\s\\d{3}\\s\\d{2}\\s\\d{2}") }
        val withDash by lazy { Regex("\\d{3}-\\d{3}-\\d{2}-\\d{2}") }
        val withoutSpace by lazy { Regex("\\d{10}") }
    }

    fun getSpecialCharactersRegex(vararg characters: Char): Regex {
        val s = characters.joinToString(separator = "") { if (it == '\\') "\\\\" else it.toString() }
        return Regex("[$s]")
    }

    fun isMatchWithoutTurkishChars(
        s1: String,
        s2: String,
        ignoreCase: Boolean = false
    ): Boolean {
        val regex = Regex("[$TURKISH_CHARACTERS]")
        val text1 =
            s1.replace(regex) {
                ENGLISH_CHARACTERS[TURKISH_CHARACTERS.indexOf(it.value)].toString()
            }
        val text2 =
            s2.replace(regex) {
                ENGLISH_CHARACTERS[TURKISH_CHARACTERS.indexOf(it.value)].toString()
            }
        return text1.equals(text2, ignoreCase)
    }
}
