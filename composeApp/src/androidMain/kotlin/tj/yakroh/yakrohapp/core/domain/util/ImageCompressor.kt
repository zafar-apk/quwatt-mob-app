package core.domain.util

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.UUID

actual class ImageCompressor(
    private val contentResolver: ContentResolver,
    private val application: Application
) {
    actual suspend fun compressImage(imageFile: ImageFile): ImageFile? = withContext(Dispatchers.IO) {

        val directory = application.applicationContext.getDir("imageDir", Context.MODE_PRIVATE)

        val file = File(directory, UUID.randomUUID().toString()+".webp")

        try {
            val inputStream: InputStream? = application.contentResolver.openInputStream(imageFile.uri)

            val bitmap: Bitmap? = BitmapFactory.decodeStream(inputStream)

            val outputStream = FileOutputStream(file)

            bitmap?.compress(Bitmap.CompressFormat.WEBP, 100, outputStream)

            outputStream.flush()
            outputStream.close()
            val compressedImageFile = Compressor.compress(application, file) {
                resolution(800, 600)
                quality(60)
                format(Bitmap.CompressFormat.WEBP)
                size(544_133)
            }
            val compressedFileUri = Uri.fromFile(compressedImageFile)
            file.delete()
            return@withContext ImageFile(
                compressedFileUri,
                contentResolver
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@withContext null
    }
}