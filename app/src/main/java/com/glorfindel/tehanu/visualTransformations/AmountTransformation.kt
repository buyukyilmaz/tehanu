package com.glorfindel.tehanu.visualTransformations

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.glorfindel.tehanu.core.TehanuDefaults
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@Stable
@Immutable
class AmountTransformation(private val patternType: PatternType = TehanuDefaults.Amount.pattern) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return amountTransformation(text)
    }

    private fun amountTransformation(text: AnnotatedString): TransformedText {
        val formatted = getFormattedText(text.text)
        val indices = mutableListOf<Int>()
        formatted.forEachIndexed { index, c -> if (c == patternType.groupingSeparator) indices.add(index - 1) }
        val amountOffsetTranslator =
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (indices.size == 0) return offset
                    if (indices.size == 1) {
                        if (offset <= indices[0]) return offset
                        return offset + 1
                    }
                    if (indices.size == 2) {
                        if (offset <= indices[0]) return offset
                        if (offset <= indices[1]) return offset + 1
                        return offset + 2
                    }
                    if (indices.size == 3) {
                        if (offset <= indices[0]) return offset
                        if (offset <= indices[1]) return offset + 1
                        if (offset <= indices[2]) return offset + 2
                        return offset + 3
                    }
                    if (indices.size == 4) {
                        if (offset <= indices[0]) return offset
                        if (offset <= indices[1]) return offset + 1
                        if (offset <= indices[2]) return offset + 2
                        if (offset <= indices[3]) return offset + 3
                        return offset + 4
                    }
                    if (indices.size == 5) {
                        if (offset <= indices[0]) return offset
                        if (offset <= indices[1]) return offset + 1
                        if (offset <= indices[2]) return offset + 2
                        if (offset <= indices[3]) return offset + 3
                        if (offset <= indices[4]) return offset + 4
                        return offset + 5
                    }
                    if (indices.size == 6) {
                        if (offset <= indices[0]) return offset
                        if (offset <= indices[1]) return offset + 1
                        if (offset <= indices[2]) return offset + 2
                        if (offset <= indices[3]) return offset + 3
                        if (offset <= indices[4]) return offset + 4
                        if (offset <= indices[5]) return offset + 5
                        return offset + 6
                    }
                    return offset
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (indices.size == 0) return offset
                    if (indices.size == 1) {
                        if (offset <= indices[0] + 1) return offset
                        return offset - 1
                    }
                    if (indices.size == 2) {
                        if (offset <= indices[0] + 1) return offset
                        if (offset <= indices[1] + 1) return offset - 1
                        return offset - 2
                    }
                    if (indices.size == 3) {
                        if (offset <= indices[0] + 1) return offset
                        if (offset <= indices[1] + 1) return offset - 1
                        if (offset <= indices[2] + 1) return offset - 2
                        return offset - 3
                    }
                    if (indices.size == 4) {
                        if (offset <= indices[0] + 1) return offset
                        if (offset <= indices[1] + 1) return offset - 1
                        if (offset <= indices[2] + 1) return offset - 2
                        if (offset <= indices[3] + 1) return offset - 3
                        return offset - 4
                    }
                    if (indices.size == 5) {
                        if (offset <= indices[0] + 1) return offset
                        if (offset <= indices[1] + 1) return offset - 1
                        if (offset <= indices[2] + 1) return offset - 2
                        if (offset <= indices[3] + 1) return offset - 3
                        if (offset <= indices[4] + 1) return offset - 4
                        return offset - 5
                    }
                    if (indices.size == 6) {
                        if (offset <= indices[0] + 1) return offset
                        if (offset <= indices[1] + 1) return offset - 1
                        if (offset <= indices[2] + 1) return offset - 2
                        if (offset <= indices[3] + 1) return offset - 3
                        if (offset <= indices[4] + 1) return offset - 4
                        if (offset <= indices[5] + 1) return offset - 5
                        return offset - 6
                    }
                    return offset
                }
            }

        return TransformedText(AnnotatedString(formatted), amountOffsetTranslator)
    }

    private fun getFormattedText(text: String): String {
        val decimal = text.substringBefore(patternType.decimalSeparator)
        val fraction = text.substringAfter(patternType.decimalSeparator, "")
        val reversed = decimal.reversed()
        var out = ""
        for (i in reversed.indices) {
            out += reversed[i]
            if (i % 3 == 2 && i != reversed.lastIndex) out += patternType.groupingSeparator
        }
        out = out.reversed()
        if (text.contains(patternType.decimalSeparator)) out += patternType.decimalSeparator
        out += fraction
        return out
    }

    companion object {
        fun check(
            text: String,
            decimalLength: Int = TehanuDefaults.Amount.decimalDigitsLength,
            fractionLength: Int = TehanuDefaults.Amount.fractionDigitsLength,
            patternType: PatternType = TehanuDefaults.Amount.pattern
        ): Boolean {
            val separator = patternType.decimalSeparator

            if (text.startsWith(separator)) return false

            if (text.contains(Regex("[^0-9$separator]"))) return false

            if (text.count { it == separator } > 1) return false

            val decimal = text.substringBefore(separator)
            val fraction = text.substringAfter(separator, "")

            if (decimal.length > decimalLength) return false
            if (fraction.length > fractionLength) return false

            return true
        }

        fun toString(
            amount: Double,
            fractionDigitsLength: Int = TehanuDefaults.Amount.fractionDigitsLength,
            patternType: PatternType = TehanuDefaults.Amount.pattern
        ): String {
            val dfs = DecimalFormatSymbols.getInstance()
            dfs.decimalSeparator = patternType.decimalSeparator
            dfs.groupingSeparator = patternType.groupingSeparator
            val pattern = "###,###." + "#".repeat(fractionDigitsLength)
            val df = DecimalFormat(pattern, dfs)
            df.roundingMode = RoundingMode.DOWN
            df.minimumFractionDigits = fractionDigitsLength
            return df.format(amount)
        }

        fun toString(
            amount: String,
            fractionDigitsLength: Int = TehanuDefaults.Amount.fractionDigitsLength,
            patternType: PatternType = TehanuDefaults.Amount.pattern
        ): String {
            val a = amount.replace(patternType.groupingSeparator.toString(), "")
            val b = toDouble(a, patternType)
            return toString(b, fractionDigitsLength, patternType)
        }

        fun toDouble(
            amount: String,
            patternType: PatternType = TehanuDefaults.Amount.pattern
        ): Double {
            return when (patternType) {
                PatternType.TR -> amount.replace(",", ".").toDouble()
                PatternType.OTHER -> amount.toDouble()
            }
        }
    }

    enum class PatternType(val decimalSeparator: Char, val groupingSeparator: Char) {
        TR(',', '.'),
        OTHER('.', ',')
    }
}
