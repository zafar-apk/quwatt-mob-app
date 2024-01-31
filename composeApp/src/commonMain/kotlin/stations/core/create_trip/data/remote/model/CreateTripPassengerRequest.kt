package stations.core.create_trip.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateTripPassengerRequest(
    @SerialName("cityFrom")
    val cityFrom: Int,
    @SerialName("cityTo")
    val cityTo: Int,
    @SerialName("addressFrom")
    val addressFrom: String,
    @SerialName("addressTo")
    val addressTo: String,
    @SerialName("count")
    val count: Int,
    @SerialName("priceFrom")
    val priceFrom: Int,
    @SerialName("priceTo")
    val priceTo: Int,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String
)

