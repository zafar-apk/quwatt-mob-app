package trips.all.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import trips.all.domain.models.Address

@Serializable
data class AddressDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("position")
    val position: String,
    @SerialName("userId")
    val userId: Int?,
    @SerialName("price")
    val price: Int
)

fun AddressDTO.toAddress(): Address = Address(
    id = id,
    position = position,
    userId = userId,
    price = price,
)