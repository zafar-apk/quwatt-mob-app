package stations.all.data.remote.model

import auth.enter_code.data.remote.model.UserDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("from_address")
    val fromAddress: String,
    @SerialName("to_address")
    val toAddress: String,
    @SerialName("from_city")
    val cityFrom: String,
    @SerialName("to_city")
    val cityTo: String,
    @SerialName("start_date")
    val date: String,
    @SerialName("start_time")
    val time: String,
    @SerialName("user")
    val driver: UserDTO,
    @SerialName("status")
    val status: String?,
    @SerialName("seats")
    val seats: List<SeatDTO>,
    @SerialName("comment")
    val comment: String?,
//    @SerialName("car")
//    val car: TransportDTO?,

//    @SerialName("passengers")
//    val passengers: List<UserDTO>,
//    @SerialName("minPrice")
//    val minPrice: Int,
//    @SerialName("maxPrice")
//    val maxPrice: Int,
//    @SerialName("availableSeatsCount")
//    val availableSeatsCount: Int,
//    @SerialName("rating")
//    val rating: Double,
)