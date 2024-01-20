package core.domain.util

import android.content.ContentResolver
import android.net.Uri

actual typealias ImageFile = ImageUri

actual fun ImageFile.toByteArray() = contentResolver.openInputStream(uri)?.use {
    it.readBytes()
} ?: throw IllegalStateException("Couldn't open inputStream $uri")

data class ImageUri(val uri: Uri, val contentResolver: ContentResolver)