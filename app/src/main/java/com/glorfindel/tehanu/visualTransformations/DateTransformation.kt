package com.glorfindel.tehanu.visualTransformations

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.glorfindel.tehanu.common.functions.tryOrNull
import com.glorfindel.tehanu.core.TehanuDefaults
import com.glorfindel.tehanu.extension.format
import com.glorfindel.tehanu.utils.Regexes
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// dd.MM.yyyy
@Stable
@Immutable
class DateTransformation(private val separator: String = TehanuDefaults.Date.separator) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        text.text.forEachIndexed { index, c ->
            out += c
            if (index == 1 || index == 3) out += separator
        }
        val offsetTranslator =
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 1) return offset
                    if (offset <= 3) return offset + 1
                    if (offset <= 8) return offset + 2
                    return 10
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset - 1
                    if (offset <= 10) return offset - 2
                    return 8
                }
            }
        return TransformedText(AnnotatedString(out), offsetTranslator)
    }

    companion object {
        fun check(date: String): Boolean {
            if (date.length > 8) return false
            if (date.contains(Regexes.nonNumeric)) return false
            return true
        }

        fun isValid(
            text: String,
            dateFormat: String = TehanuDefaults.Date.format
        ): Boolean {
            return if (text.matches(Regexes.Date.withDot) || text.matches(Regexes.Date.withSlash)) {
                val d = tryOrNull { LocalDate.parse(text, DateTimeFormatter.ofPattern(dateFormat)) }
                d != null
            } else {
                false
            }
        }

        fun toString(
            date: String,
            separator: String = TehanuDefaults.Date.separator,
            format: String = "##$separator##$separator####"
        ): String {
            return Regexes.nonNumeric.replace(date, "").take(8).format(format, separator)
        }
    }
}
