package stations.all.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectorDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("type")
    val type: ConnectorTypeDTO,
    @SerialName("maxPower")
    val maxPower: Int,
    @SerialName("rate")
    val rate: Float,
    @SerialName("isAvailable")
    val isAvailable: Boolean
)