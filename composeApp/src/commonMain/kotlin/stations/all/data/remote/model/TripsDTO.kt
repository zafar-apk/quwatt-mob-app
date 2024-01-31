package stations.all.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TripsDTO(
//    @SerialName("isDriver")
//    val isDriver: Boolean?,
    @SerialName("result")
    val trips: List<TripDTO>
)