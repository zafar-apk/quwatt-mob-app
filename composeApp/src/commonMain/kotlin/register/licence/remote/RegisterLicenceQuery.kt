package register.licence.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterLicenceQuery(
    @SerialName("licenceNumber")
    val licenceNumber: String,
    @SerialName("expirationDate")
    val expirationDate: String
)