package trips.all.domain.models

import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportType
import core.domain.cities.model.City

data class TripsFilter(
    val autoType: TransportType?,
    val autoModel: TransportBrand?,
    val fromPriceTrip: Int,
    val toPriceTrip: Int,
    val fromCity: City?,
    val toCity: City?,
    val tripDate: String?,
    val tripTime: String?,
    val tripRating: Double?
)