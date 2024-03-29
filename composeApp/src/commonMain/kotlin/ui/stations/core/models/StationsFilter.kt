package ui.stations.core.models

import ui.core.presentation.models.CityParcelize

data class StationsFilter(
    val autoType: String?,
    val autoModel: String?,
    val fromPriceTrip: Int,
    val toPriceTrip: Int,
    val fromCity: CityParcelize?,
    val toCity: CityParcelize?,
    val tripDate: String?,
    val tripTime: String?,
    val tripRating: Double?
)