package profile.domain

import profile.domain.transport.Transport

data class User(
    val id: Int,
    val phone: String,
    val photo: String?,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val dateOfBirth: String,
    val isDriver: Boolean,
    val transport: Transport?,
    val licenceNumber: String?,
    val licenceExpiration: String?,
    val passportNumber: String?,
    val rating: Double,
) {
    fun getFullName(): String = "$name $surname $patronymic"
}
