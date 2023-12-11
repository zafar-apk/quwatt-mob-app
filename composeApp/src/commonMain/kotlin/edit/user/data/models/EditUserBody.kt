package edit.user.data.models

import kotlinx.serialization.SerialName
import edit.transport.data.EditTransportDTO

@kotlinx.serialization.Serializable
class EditUserBody(
    @SerialName("name")
    val name: String? = null,
    @SerialName("surname")
    val surname: String? = null,
    @SerialName("patronymic")
    val patronymic: String? = null,
    @SerialName("dateOfBirth")
    val dateOfBirth: String? = null,
    @SerialName("transport")
    val transport: EditTransportDTO? = null,
    @SerialName("licenceNumber")
    val licenceNumber: String? = null,
    @SerialName("licenceExpiration")
    val licenceExpiration: String? = null,
    @SerialName("passportNumber")
    val passportNumber: String? = null
)