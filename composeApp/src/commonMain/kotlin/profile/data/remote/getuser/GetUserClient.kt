package profile.data.remote.getuser

import core.domain.util.Resource
import profile.domain.User

interface GetUserClient {
    suspend fun getUser(): Resource<User>
}