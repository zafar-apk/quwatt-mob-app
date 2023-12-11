package trips.all.data.remote.model

import kotlinx.serialization.SerialName
import auth.enter_code.data.remote.model.UserDTO
import core.data.remote.cities.model.CityDTO
import kotlinx.serialization.Serializable

@Serializable
data class TripDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("addressFrom")
    val fromAddress: String,
    @SerialName("addressTo")
    val toAddress: String,
    @SerialName("cityFrom")
    val cityFrom: CityDTO,
    @SerialName("cityTo")
    val cityTo: CityDTO,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String,
    @SerialName("driver")
    val driver: UserDTO,
    @SerialName("status")
    val status: String,
    @SerialName("seats")
    val seats: List<SeatDTO>,
    @SerialName("passengers")
    val passengers: List<UserDTO>,
    @SerialName("minPrice")
    val minPrice: Int,
    @SerialName("maxPrice")
    val maxPrice: Int,
    @SerialName("availableSeatsCount")
    val availableSeatsCount: Int,
    @SerialName("rating")
    val rating: Double,
)