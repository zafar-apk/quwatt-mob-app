package trips.core.create_trip.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import trips.all.data.remote.model.SeatDTO

@Serializable
class CreateTripDriverRequest(
    @SerialName("cityFrom")
    val cityFrom: Int,
    @SerialName("addressFrom")
    val addressFrom: String,
    @SerialName("cityTo")
    val cityTo: Int,
    @SerialName("addressTo")
    val addressTo: String,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String,
    @SerialName("seats")
    val seats: List<SeatDTO>
)

