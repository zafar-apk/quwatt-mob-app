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
    val name: String?,
    @SerialName("surname")
    val surname: String?,
    @SerialName("patronymic")
    val patronymic: String?,
    @SerialName("dateOfBirth")
    val dateOfBirth: String?,
    @SerialName("photo")
    val photo: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("balance")
    val balance: Double? = null
)

fun UserDTO.toUser() = User(
    id = id,
    phone = phone,
    name = name.orEmpty(),
    surname = surname.orEmpty(),
    patronymic = patronymic,
    dateOfBirth = dateOfBirth.orEmpty(),
    photo = photo,
    email = email,
    balance = balance
)