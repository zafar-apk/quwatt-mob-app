package core.data.remote

import core.data.remote.cities.model.CityDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDTO(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("city")
    val city: CityDTO,
    @SerialName("address")
    val address: String
)
