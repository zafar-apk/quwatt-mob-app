package profile.data.remote.getuser

import core.domain.util.Resource
import profile.domain.User

class GetUser(
    private val client: GetUserClient
) {

    suspend fun execute(): Resource<User> = client.getUser()
}