package register.user.data.remote

import core.domain.util.ImageFile
import core.domain.util.Resource

interface RegisterUserClient {
    suspend fun register(
        name: String,
        surname: String,
        patronymic: String,
        dateOfBirth: String,
        photo: ByteArray?
    ): Resource<Boolean>
}