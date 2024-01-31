package stations.all.data.remote.model

import core.domain.util.toBoolean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import stations.all.domain.models.Seat

@Serializable
data class SeatDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("seat_number")
    val position: String,
    @SerialName("is_free")
    val isAvailable: Int,
    @SerialName("price")
    val price: Int
)

fun SeatDTO.toSeat() = Seat(
    id = id,
    position = position,
    price = price,
    isAvailable = isAvailable.toBoolean()
)