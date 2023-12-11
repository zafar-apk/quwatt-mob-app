package passengers.all.data.model

import kotlinx.serialization.SerialName
import auth.enter_code.data.remote.model.UserDTO
import core.data.remote.cities.model.CityDTO
import kotlinx.serialization.Serializable

@Serializable
data class PassengerDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("cityFrom")
    val cityFrom: CityDTO,
    @SerialName("cityTo")
    val cityTo: CityDTO,
    @SerialName("addressFrom")
    val addressFrom: String,
    @SerialName("addressTo")
    val addressTo: String,
    @SerialName("count")
    val count: Int,
    @SerialName("priceFrom")
    val priceFrom: Int,
    @SerialName("priceTo")
    val priceTo: Int,
    @SerialName("user")
    val user: UserDTO,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String,
    @SerialName("rating")
    val rating: Double,
    @SerialName("driverId")
    val driverId: Int,
    @SerialName("requests")
    val requests: List<UserDTO>
)