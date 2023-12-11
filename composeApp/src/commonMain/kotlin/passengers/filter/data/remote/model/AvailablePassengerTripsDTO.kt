package passengers.filter.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AvailablePassengerTripsDTO(
    @SerialName("count")
    val count: Int
)