package com.glorfindel.tehanu.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.glorfindel.tehanu.core.TehanuDefaults

class AnnotatedStringBuilder(
    private val text: String,
    private val startIdentifier: String = TehanuDefaults.AnnotatedString.startIdentifier,
    private val endIdentifier: String = TehanuDefaults.AnnotatedString.endIdentifier
) {
    private var color: Color? = null
    private var underline: Boolean = false
    private var bold: Boolean = false

    fun color(color: Color): AnnotatedStringBuilder {
        this.color = color
        return this
    }

    fun underline(): AnnotatedStringBuilder {
        underline = true
        return this
    }

    fun bold(): AnnotatedStringBuilder {
        bold = true
        return this
    }

    fun build(): AnnotatedString {
        val data = getData(text) ?: return buildAnnotatedString { append(text) }

        return buildAnnotatedString {
            append(data.text.substring(0, data.startIndex))
            withStyle(
                style =
                    SpanStyle(
                        color = color ?: Color.Unspecified,
                        textDecoration = if (underline) TextDecoration.Underline else null,
                        fontWeight = if (bold) FontWeight.W700 else null
                    )
            ) {
                append(data.text.substring(data.startIndex, data.endIndex))
            }
            append(data.text.substring(data.endIndex, data.text.length))
        }
    }

    private fun getData(text: String): AnnotatedStringData? {
        val start = text.indexOfFirst { it.toString() == startIdentifier }
        val end = text.indexOfFirst { it.toString() == endIdentifier }
        if (start == -1 || end == -1) return null
        val newText = text.replaceFirst(startIdentifier, "").replaceFirst(endIdentifier, "")

        return AnnotatedStringData(newText, start, end - 1)
    }
}

data class AnnotatedStringData(val text: String, val startIndex: Int, val endIndex: Int)
