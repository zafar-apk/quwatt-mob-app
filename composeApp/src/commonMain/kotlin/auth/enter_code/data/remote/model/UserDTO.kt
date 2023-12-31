package auth.enter_code.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import profile.domain.User

@Serializable
data class ProfileDTO(
    @SerialName("result")
    val result: UserDTO?
)

@Serializable
data class UserDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("phone_number")
    val phone: String,
    @SerialName("first_name")
    val name: String?,
    @SerialName("last_name")
    val surname: String?,
    @SerialName("patronymic")
    val patronymic: String?,
    @SerialName("date_of_birth")
    val dateOfBirth: String?,
    @SerialName("isDriver")
    val isDriver: Boolean = false,
    @SerialName("transport")
    val transport: TransportDTO? = null,
    @SerialName("licenceNumber")
    val licenceNumber: String? = null,
    @SerialName("licenceExpiration")
    val licenceExpiration: String? = null,
    @SerialName("passportNumber")
    val passportNumber: String? = null,
    @SerialName("avatar")
    val photo: String? = null,
    @SerialName("rating")
    val rating: Double = 0.0
)

fun UserDTO.toUser() = User(
    id = id,
    phone = phone,
    name = name.orEmpty(),
    surname = surname.orEmpty(),
    patronymic = patronymic,
    dateOfBirth = dateOfBirth.orEmpty(),
    isDriver = isDriver,
    transport = transport?.toTransport(),
    licenceNumber = licenceNumber,
    licenceExpiration = licenceExpiration,
    passportNumber = passportNumber,
    photo = photo,
    rating = rating
)