package stations.all.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationDetailsDTO(
    @SerialName("station")
    val stationDTO: StationDTO,
    @SerialName("connectors")
    val connectors: List<ConnectorDTO>
)
