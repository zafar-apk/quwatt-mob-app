package passengers.all.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PassengerFilterBody(
    @SerialName("priceFrom")
    val fromPriceTrip: Int,
    @SerialName("priceTo")
    val toPriceTrip: Int,
    @SerialName("cityFrom")
    val fromCityId: Int?,
    @SerialName("cityTo")
    val toCityId: Int?,
    @SerialName("date")
    val tripDate: String?,
    @SerialName("time")
    val tripTime: String?,
    @SerialName("rating")
    val tripRating: Double?
)