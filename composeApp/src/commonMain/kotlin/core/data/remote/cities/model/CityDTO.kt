package core.data.remote.cities.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitiesDTO(
    @SerialName("result")
    val result: List<CityDTO>?
)

@Serializable
data class CityDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)

