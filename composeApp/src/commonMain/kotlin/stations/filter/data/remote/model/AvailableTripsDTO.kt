package tj.ham_safar.app.trips.filter.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AvailableTripsDTO(
    @SerialName("count")
    val count: Int
)