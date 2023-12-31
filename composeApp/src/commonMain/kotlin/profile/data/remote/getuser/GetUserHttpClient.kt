package profile.data.remote.getuser

import auth.enter_code.data.remote.model.ProfileDTO
import auth.enter_code.data.remote.model.UserDTO
import auth.enter_code.data.remote.model.toUser
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import profile.domain.User

class GetUserHttpClient(
    private val client: HttpClient
) : GetUserClient {

    private fun mapUser(dto: ProfileDTO): User? = dto?.result?.toUser()

    override suspend fun getUser(): Resource<User?> = networkCall(
        map = ::mapUser,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/users/profile")
                contentType(ContentType.Application.Json)
            }
        }
    )
}