package stations.all.domain.models

import core.domain.cities.model.City

data class Location(
    val latitude: Double,
    val longitude: Double,
    val city: City,
    val address: String
)
