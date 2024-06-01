package com.glorfindel.tehanu.infrastructure.splashImagePicker

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.glorfindel.tehanu.common.functions.checkNull
import com.glorfindel.tehanu.common.functions.tryOrNull
import com.glorfindel.tehanu.infrastructure.Mapper
import com.glorfindel.tehanu.infrastructure.Winehouse
import com.glorfindel.tehanu.utils.DateUtils
import java.io.File
import java.util.Calendar

class SplashImagePicker(private val context: Context, private val winehouse: Winehouse, private val mapper: Mapper) {
    companion object {
        internal const val KEY = "tehanuSplashImages"
    }

    fun getSplashData(): SplashData? {
        val image = getImage() ?: return null
        val dirPath = context.filesDir.path.toString() + "/$KEY"
        val dir = File(dirPath)
        if (dir.exists().not()) {
            return null
        }
        val file = dir.listFiles()?.find { it.name == image.id }
        val byteArray =
            if (file == null) {
                null
            } else {
                tryOrNull { file.readBytes() }
            }
        return checkNull(byteArray) { SplashData(image, it) }
    }

    private fun getImage(): SplashImage? {
        val now = DateUtils.toString(Calendar.getInstance(), DateUtils.FORMAT_1)
        val images = winehouse.getObjectSync(KEY, SplashImages::class.java)?.images
        images?.forEach {
            if (it.dates?.contains(now) == true) {
                return it
            }
        }
        return null
    }

    fun save(splashImages: SplashImages) {
        val images = splashImages.images
        val savedImages = winehouse.getObjectSync(KEY, SplashImages::class.java)?.images
        images?.forEach { image ->
            val i = savedImages?.find { it.id == image.id }
            if (i == null || image.hash != i.hash) {
                val si = mapper.toJson(splashImages)
                val data = Data.Builder().apply { putString(KEY, si) }.build()
                val workManager = WorkManager.getInstance(context)
                val work = OneTimeWorkRequestBuilder<SplashImageWorker>().setInputData(data).build()
                val continuation = workManager.beginUniqueWork(KEY, ExistingWorkPolicy.REPLACE, work)
                continuation.enqueue()
                return@forEach
            }
        }
    }
}

data class SplashData(
    val splashImageData: SplashImage?,
    val byteArray: ByteArray
)

data class SplashImage(
    val id: String,
    val hash: String,
    val dates: List<String>?,
    val url: String
)

data class SplashImages(
    val images: List<SplashImage>?
)
