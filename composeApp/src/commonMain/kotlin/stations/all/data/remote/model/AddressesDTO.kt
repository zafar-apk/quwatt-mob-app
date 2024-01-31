package stations.all.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import stations.all.domain.models.Addresses

@Serializable
data class AddressesDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("fromAddress")
    val fromAddress: AddressDTO,
    @SerialName("toAddress")
    val toAddress: AddressDTO
)

fun AddressesDTO.toAddresses(): Addresses =
    Addresses(id = id, fromAddress = fromAddress.toAddress(), toAddress = toAddress.toAddress())