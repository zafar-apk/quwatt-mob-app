package trips.all.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import trips.all.domain.models.Seat

@Serializable
data class SeatDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("position")
    val position: String,
    @SerialName("userId")
    val userId: Int?,
    @SerialName("isAvailable")
    val isAvailable: Boolean,
    @SerialName("price")
    val price: Int
)

fun SeatDTO.toSeat() = Seat(
    id = id,
    position = position,
    userId = userId,
    price = price,
)