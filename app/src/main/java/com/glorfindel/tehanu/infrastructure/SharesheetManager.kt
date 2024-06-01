package com.glorfindel.tehanu.infrastructure

import android.content.Context
import android.content.Intent
import android.net.Uri

class SharesheetManager {
    fun sendText(
        context: Context,
        text: String,
        title: String? = null
    ) {
        val shareIntent: Intent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
        context.startActivity(Intent.createChooser(shareIntent, title))
    }

    fun sendImage(
        context: Context,
        uriToImage: Uri,
        title: String? = null
    ) {
        val shareIntent: Intent =
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uriToImage)
                type = "image/*"
            }
        context.startActivity(Intent.createChooser(shareIntent, title))
    }
}
