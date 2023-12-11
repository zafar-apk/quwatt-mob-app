package ui.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import core.domain.util.asRemoteImage
import io.kamel.core.getOrNull
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun getImagePainterOrPlaceHolder(
    photo: String?,
    placeholderResId: String
): Painter = if (photo.isNullOrEmpty()) {
    painterResource(placeholderResId)
} else {
    asyncPainterResource(data = photo.asRemoteImage()).getOrNull() ?: painterResource(
        placeholderResId
    )
}