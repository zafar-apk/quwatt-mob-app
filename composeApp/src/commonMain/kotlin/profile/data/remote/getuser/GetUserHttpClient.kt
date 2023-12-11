package profile.data.remote.getuser

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import auth.enter_code.data.remote.model.UserDTO
import auth.enter_code.data.remote.model.toUser
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import profile.domain.User

class GetUserHttpClient(
    private val client: HttpClient
) : GetUserClient {

    private fun mapUser(dto: UserDTO): User = dto.toUser()

    override suspend fun getUser(): Resource<User> = networkCall(
        map = ::mapUser,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/user")
                contentType(ContentType.Application.Json)
            }
        }
    )
}