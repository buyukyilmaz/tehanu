package com.glorfindel.tehanu.infrastructure

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardManager(private val context: Context) {
    fun copy(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }

    fun getLastCopiedText(): String {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val item = clipboard.primaryClip?.getItemAt(0)?.text?.toString()
        return item ?: ""
    }

    fun getLastCopiedHTMLText(): String {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val item = clipboard.primaryClip?.getItemAt(0)?.htmlText
        return item ?: ""
    }

    fun clearClipboard() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("label", "")
        clipboard.setPrimaryClip(clip)
    }
}
