package ui.stations.all.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import core.domain.util.asRemoteImage
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import io.kamel.core.getOrNull
import io.kamel.image.asyncPainterResource
import stations.all.domain.models.Station
import tj.quwatt.quwattapp.SharedRes

fun Station.getDisplayAddress(): String {
    return "${location.city.name}, ${location.address}"
}

@Composable
fun getStationPainter(image: String): Painter {
    return asyncPainterResource(image.asRemoteImage()).getOrNull()
        ?: painterResource(SharedRes.images.placeholder)
}

@Composable
fun getFavoriteButtonPainter(favorite: Boolean): Painter {
    return painterResource(
        if (favorite) SharedRes.images.ic_heart_filled
        else SharedRes.images.ic_heart
    )
}

@Composable
fun getStationPrices(station: Station): String {
    return buildString {
        append("1 kw ")
        append(
            if (station.rateTo != null) {
                "${station.rateFrom} - ${station.rateTo} ${stringResource(SharedRes.strings.somoni)}"
            } else {
                "${station.rateFrom} ${stringResource(SharedRes.strings.somoni)}"
            }
        )
    }
}

fun getStationPower(station: Station): String {
    return buildString {
        append(station.powerFrom)
        if (station.powerTo != null) {
            append(" - ")
            append(station.powerTo)
        }
        append(" kw")
    }
}