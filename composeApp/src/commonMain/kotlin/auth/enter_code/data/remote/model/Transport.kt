package auth.enter_code.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import profile.domain.transport.Transport
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType

@Serializable
data class TransportDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("photo")
    val photo: String?,
    @SerialName("type")
    val type: TransportType,
    @SerialName("brand")
    val brand: TransportBrand,
    @SerialName("model")
    val model: String,
    @SerialName("color")
    val colors: TransportColors,
    @SerialName("capacity")
    val capacity: Int,
    @SerialName("dateOfIssue")
    val dateOfIssue: String,
    @SerialName("hasConditioner")
    val hasConditioner: Boolean
)

fun TransportDTO.toTransport() = Transport(
    id = id,
    photo = photo,
    type = type,
    brand = brand,
    model = model,
    colors = colors,
    capacity = capacity,
    dateOfIssue = dateOfIssue,
    hasConditioner = hasConditioner,
)
