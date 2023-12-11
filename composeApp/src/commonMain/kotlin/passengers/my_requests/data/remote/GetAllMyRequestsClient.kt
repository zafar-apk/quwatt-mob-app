package passengers.my_requests.data.remote

import core.domain.util.Resource
import passengers.all.domain.Passenger

interface GetAllMyRequestsClient {

    suspend fun getAll(): Resource<List<Passenger>>
}