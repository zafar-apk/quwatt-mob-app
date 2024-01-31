package register.user.domain

import core.domain.util.Resource
import register.user.data.remote.RegisterUserClient

class RegisterUser(private val registerUserClient: RegisterUserClient) {
    suspend fun execute(
        name: String,
        surname: String,
        patronymic: String,
        dateOfBirth: String,
        photo: ByteArray?,
    ): Resource<Boolean> = registerUserClient.register(
        name = name,
        surname = surname,
        patronymic = patronymic,
        dateOfBirth = dateOfBirth,
        photo = photo
    )
}