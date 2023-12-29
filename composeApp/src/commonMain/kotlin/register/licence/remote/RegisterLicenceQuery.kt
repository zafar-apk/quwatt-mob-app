package register.licence.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterLicenceQuery(
    @SerialName("serial_number")
    val serialNumber: String,
    @SerialName("valid_until")
    val validUntil: String
)