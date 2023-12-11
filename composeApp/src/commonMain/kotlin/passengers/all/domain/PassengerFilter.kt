package passengers.all.domain

import core.domain.cities.model.City

data class PassengerFilter(
    val fromPriceTrip: Int,
    val toPriceTrip: Int,
    val fromCity: City?,
    val toCity: City?,
    val tripDate: String?,
    val tripTime: String?,
    val tripRating: Double?
)