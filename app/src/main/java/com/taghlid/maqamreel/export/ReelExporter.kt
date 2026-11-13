package com.taghlid.maqamreel.export

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

/**
 * خروجی گرفتن از فریم ریل به‌صورت PNG با ابعاد دقیق ۱۰۸۰×۱۹۲۰.
 *
 * روش: محتوا در یک GraphicsLayer ضبط می‌شود (روش رسمی Compose برای
 * اسکرین‌شات composable) و سپس به اندازهٔ نهایی مقیاس می‌خورد.
 */
class ReelExporter(private val context: Context) {

    /** خروجی از لایهٔ ضبط‌شده، مقیاس‌خورده به ابعاد ریل. */
    fun export(layer: GraphicsLayer): Bitmap {
        val raw = layer.toImageBitmap().asAndroidBitmap()
        if (raw.width == REEL_WIDTH_PX && raw.height == REEL_HEIGHT_PX) return raw
        val scaled = Bitmap.createScaledBitmap(raw, REEL_WIDTH_PX, REEL_HEIGHT_PX, true)
        if (scaled != raw) raw.recycle()
        return scaled
    }

    /** ذخیره در گالری: Pictures/MaqamReel — آمادهٔ آپلود در اینستاگرام. */
    fun saveToGallery(bitmap: Bitmap, fileName: String): Uri {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MaqamReel")
        }
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?: error("MediaStore insert failed")
        resolver.openOutputStream(uri)?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        } ?: error("openOutputStream failed")
        return uri
    }

    /** اشتراک‌گذاری مستقیم با اپ‌های دیگر (اینستاگرام و…). */
    fun share(bitmap: Bitmap, fileName: String): Intent {
        val dir = File(context.cacheDir, "reels").apply { mkdirs() }
        val file = File(dir, fileName)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        val uri = FileProvider.getUriForFile(
            context, "${context.packageName}.fileprovider", file
        )
        return Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    companion object {
        const val REEL_WIDTH_PX = 1080
        const val REEL_HEIGHT_PX = 1920
    }
}
