package stations.all.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportType

@Serializable
class TripFilterBody(
    @SerialName("type")
    val autoType: TransportType?,
    @SerialName("brand")
    val autoModel: TransportBrand?,
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