package core.domain.util

expect class ImageCompressor {
    suspend fun compressImage(imageFile: ImageFile): ImageFile?
}