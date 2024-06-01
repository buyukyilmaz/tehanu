package com.glorfindel.tehanu.infrastructure.splashImagePicker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.glorfindel.tehanu.infrastructure.Mapper
import com.glorfindel.tehanu.infrastructure.Winehouse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.File
import java.net.URL

class SplashImageWorker(private val context: Context, private val winehouse: Winehouse, params: WorkerParameters) : CoroutineWorker(context, params) {
    private var dirPath = ""
    private val mapper = Mapper()
    private var images: List<SplashImage>? = null
    private var savedImages: List<SplashImage>? = null

    override suspend fun doWork(): Result {
        dirPath = context.filesDir.path.toString() + "/${SplashImagePicker.KEY}"
        val dir = File(dirPath)
        if (dir.exists().not()) {
            dir.mkdir()
        }
        images = mapper.toObject(inputData.getString(SplashImagePicker.KEY), SplashImages::class.java)?.images
        savedImages = winehouse.getObjectSync(SplashImagePicker.KEY, SplashImages::class.java)?.images
        val result = downloadImages()
        return if (result) {
            winehouse.storeObject(SplashImages(images), SplashImagePicker.KEY)
            Result.success()
        } else {
            dir.listFiles()?.forEach { it.delete() }
            winehouse.deleteString(SplashImagePicker.KEY)
            Result.failure()
        }
    }

    private suspend fun downloadImages(): Boolean {
        images?.forEach { image ->
            val i = savedImages?.find { it.id == image.id }
            if (i == null || i.hash != image.hash) {
                val job = CoroutineScope(Dispatchers.IO).async { downloadImage(image) }
                val result = job.await()
                if (result.not()) {
                    return false
                }
            }
        }
        return true
    }

    private fun downloadImage(image: SplashImage): Boolean {
        return try {
            val url = URL(image.url)
            val imageByteArray = url.readBytes()
            val imageFile = File(dirPath, image.id)
            imageFile.writeBytes(imageByteArray)
            true
        } catch (e: Exception) {
            false
        }
    }
}
