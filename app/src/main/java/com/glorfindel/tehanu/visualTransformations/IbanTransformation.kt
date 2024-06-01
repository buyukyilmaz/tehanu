package com.glorfindel.tehanu.visualTransformations

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.glorfindel.tehanu.extension.format
import com.glorfindel.tehanu.utils.Regexes

// ## #### #### #### #### #### ##
@Stable
@Immutable
object IbanTransformation : VisualTransformation {
    private fun format(iban: String): String {
        var out = ""
        iban.forEachIndexed { index, c ->
            out += c
            if (listOf(1, 5, 9, 13, 17, 21).contains(index)) out += " "
        }
        return out
    }

    private val offsetTranslator =
        object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 5) return offset + 1
                if (offset <= 9) return offset + 2
                if (offset <= 13) return offset + 3
                if (offset <= 17) return offset + 4
                if (offset <= 21) return offset + 5
                if (offset <= 24) return offset + 6
                return 30
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 7) return offset - 1
                if (offset <= 12) return offset - 2
                if (offset <= 17) return offset - 3
                if (offset <= 22) return offset - 4
                if (offset <= 27) return offset - 5
                if (offset <= 30) return offset - 6
                return 24
            }
        }

    override fun filter(text: AnnotatedString) = TransformedText(AnnotatedString(format(text.text)), offsetTranslator)

    fun check(text: String): Boolean {
        if (text.length > 24) return false

        if (text.contains(Regexes.nonNumeric)) return false

        return true
    }

    fun toStringWithoutPrefixWithSpace(text: String) = text.replace(Regexes.nonNumeric, "").take(24).format("## #### #### #### #### #### ##", " ")

    fun toStringWithoutPrefixWithoutSpace(text: String) = text.replace(Regexes.nonNumeric, "").take(24)

    fun toStringWithPrefixWithSpace(text: String) = "TR${toStringWithoutPrefixWithSpace(text)}"

    fun toStringWithPrefixWithoutSpace(text: String) = "TR${toStringWithoutPrefixWithoutSpace(text)}"

    fun isValid(text: String): Boolean {
        if (text.matches(Regexes.Iban.withPrefixWithSpace)) return true
        if (text.matches(Regexes.Iban.withPrefixWithoutSpace)) return true
        if (text.matches(Regexes.Iban.withoutPrefixWithSpace)) return true
        if (text.matches(Regexes.Iban.withoutPrefixWithoutSpace)) return true
        return false
    }
}
