package ui.passengers.core.shared_trip_filter

import ui.core.presentation.models.CityParcelize

class PassengerTripsFilterParcelize(
    val fromPriceTrip: Int,
    val toPriceTrip: Int,
    val fromCity: CityParcelize?,
    val toCity: CityParcelize?,
    val tripDate: String?,
    val tripTime: String?,
    val tripRating: Double?
)