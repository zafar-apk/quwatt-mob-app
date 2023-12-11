package edit.transport.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType

@Serializable
class EditTransportDTO(
    @SerialName("id")
    val id: Int,
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
