package ui.stations.details.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import core.domain.util.asRemoteImage
import core.presentation.LocalPaddings
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import io.kamel.core.getOrNull
import io.kamel.image.asyncPainterResource
import stations.all.domain.models.Connector
import stations.all.domain.models.ConnectorType
import tj.quwatt.quwattapp.SharedRes
import ui.theme.DarkRed
import ui.theme.Gray
import ui.theme.Green
import kotlin.math.roundToInt

@Composable
fun ConnectorItem(connector: Connector, modifier: Modifier = Modifier) {
    val paddings = LocalPaddings.current
    Card(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = getConnectorTypePainter(connector.type),
                    contentDescription = stringResource(SharedRes.strings.connector_type_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(114.dp)
                        .width(135.dp)
                        .padding(paddings.small)
                )

                Column {
                    Text(
                        text = "№ ${connector.type.id}",
                        style = MaterialTheme.typography.body2,
                        color = Gray,
                        modifier = Modifier.padding(top = paddings.small),
                    )

                    Text(
                        text = connector.type.name,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = paddings.mini)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = paddings.small)
                    ) {
                        Icon(
                            painter = painterResource(SharedRes.images.ic_dollar),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(paddings.mini))

                        Text(
                            text = connector.price.roundToInt().toString(),
                            style = MaterialTheme.typography.body1
                        )
                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = paddings.small)
                    ) {
                        Icon(
                            painter = painterResource(SharedRes.images.ic_lightning),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(paddings.mini))

                        Text(
                            text = "${connector.power} kW",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }

            Text(
                text = getConnectorStatusText(connector),
                style = MaterialTheme.typography.caption,
                color = Color.White,
                modifier = Modifier.align(Alignment.TopEnd)
                    .padding(paddings.small)
                    .background(
                        color = getStatusColor(connector.isAvailable),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = paddings.mini)
            )
        }
    }
}

fun getStatusColor(isAvailable: Boolean): Color {
    return if (isAvailable) Green else DarkRed
}

@Composable
fun getConnectorStatusText(connector: Connector): String {
    return stringResource(
        if (connector.isAvailable) {
            SharedRes.strings.available
        } else {
            SharedRes.strings.busy
        }
    )
}

@Composable
fun getConnectorTypePainter(type: ConnectorType): Painter {
    return painterResource(SharedRes.images.placeholder_small)
    return asyncPainterResource(
        data = type.image.asRemoteImage()
    ).getOrNull() ?: painterResource(SharedRes.images.placeholder_small)
}
