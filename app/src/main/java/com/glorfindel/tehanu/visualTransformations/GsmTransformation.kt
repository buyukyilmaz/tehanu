package com.glorfindel.tehanu.visualTransformations

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.glorfindel.tehanu.core.TehanuDefaults
import com.glorfindel.tehanu.utils.Regexes

@Stable
@Immutable
class GsmTransformation(private val type: GsmTransformationType = GsmTransformationType.DASH) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        text.text.forEachIndexed { index, c ->
            out += c
            if (index == 2 || index == 5 || index == 7) out += type.separator
        }

        val defaultOffsetMapping =
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset + 1
                    if (offset <= 7) return offset + 2
                    if (offset <= 10) return offset + 3
                    return 13
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 7) return offset - 1
                    if (offset <= 10) return offset - 2
                    if (offset <= 13) return offset - 3
                    return 10
                }
            }

        return TransformedText(AnnotatedString(out), defaultOffsetMapping)
    }

    companion object {
        fun check(text: String): Boolean {
            if (text.startsWith("0")) return false
            if (text.contains(Regexes.nonNumeric)) return false
            if (text.length > 10) return false
            return true
        }

        fun isValid(text: String): Boolean {
            if (text.startsWith("5").not()) return false
            if (text.matches(Regexes.Gsm.withSpace) || text.matches(Regexes.Gsm.withDash) || text.matches(Regexes.Gsm.withoutSpace)) return true
            return false
        }

        fun toStringWithoutFormat(text: String): String {
            return Regexes.nonNumeric.replace(text, "").take(10)
        }

        fun toStringWithFormat(
            text: String,
            type: GsmTransformationType = TehanuDefaults.Gsm.type
        ): String {
            val gsm = toStringWithoutFormat(text)
            return gsm.format("###${type.separator}###${type.separator}##${type.separator}##", type.separator)
        }
    }
}

enum class GsmTransformationType(val separator: String) {
    DASH("-"), // ###-###-##-##
    SPACE(" ") // ### ### ## ##
}
