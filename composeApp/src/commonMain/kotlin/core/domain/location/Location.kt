package core.domain.location

import core.domain.cities.model.City

data class Location(
    val latitude: Double,
    val longitude: Double,
    val city: City,
    val address: String
)
