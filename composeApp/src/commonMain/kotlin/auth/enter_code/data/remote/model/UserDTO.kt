package auth.enter_code.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import profile.domain.User

@Serializable
data class UserDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("phone")
    val phone: String,
    @SerialName("name")
    val name: String,
    @SerialName("surname")
    val surname: String,
    @SerialName("patronymic")
    val patronymic: String?,
    @SerialName("dateOfBirth")
    val dateOfBirth: String,
    @SerialName("isDriver")
    val isDriver: Boolean,
    @SerialName("transport")
    val transport: TransportDTO? = null,
    @SerialName("licenceNumber")
    val licenceNumber: String? = null,
    @SerialName("licenceExpiration")
    val licenceExpiration: String? = null,
    @SerialName("passportNumber")
    val passportNumber: String? = null,
    @SerialName("photo")
    val photo: String? = null,
    @SerialName("rating")
    val rating: Double = 0.0
)

fun UserDTO.toUser() = User(
    id = id,
    phone = phone,
    name = name,
    surname = surname,
    patronymic = patronymic,
    dateOfBirth = dateOfBirth,
    isDriver = isDriver,
    transport = transport?.toTransport(),
    licenceNumber = licenceNumber,
    licenceExpiration = licenceExpiration,
    passportNumber = passportNumber,
    photo = photo,
    rating = rating
)