package edit.transport.domain

import core.domain.util.Resource
import profile.data.remote.getuser.GetUserClient
import profile.domain.transport.Transport

class GetTransportUseCase(
    private val client: GetUserClient
) {
    suspend operator fun invoke(): Resource<Transport> {
        val result = client.getUser()
        return if (result is Resource.Success) {
            TODO()
//            Resource.Success(result.data!!.transport!!)
        } else {
            Resource.Error(Throwable(result.throwable))
        }
    }
}