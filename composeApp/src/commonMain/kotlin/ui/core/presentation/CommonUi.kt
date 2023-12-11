package ui.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun getImagePainterOrPlaceHolder(
    photo: String?,
    placeholderResId: String
): Painter = if (photo.isNullOrEmpty()) {
    painterResource(placeholderResId)
} else {
    painterResource(placeholderResId)
// TODO find and use kmp loader
//    rememberAsyncImagePainter(
//        model = photo.asRemoteImage(),
//        error = painterResource(id = placeholderResId),
//        placeholder = painterResource(id = placeholderResId)
//    )
}