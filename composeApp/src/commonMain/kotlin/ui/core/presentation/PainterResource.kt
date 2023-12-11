package ui.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.getImageByFileName
import tj.yakroh.yakrohapp.SharedRes

@Composable
fun painterResource(resName: String): Painter {
    return painterResource(getImageByFileName(resName))
}

private fun getImageByFileName(name: String): ImageResource {
    val fallbackImage = SharedRes.images.ic_yakroh
    return SharedRes.images.getImageByFileName(name.substringBeforeLast(".xml")) ?: fallbackImage
}