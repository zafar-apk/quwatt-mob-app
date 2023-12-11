package passengers.my_requests.domain

import core.domain.util.Resource
import passengers.all.domain.Passenger
import passengers.my_requests.data.remote.GetAllMyRequestsClient

class GetAllMyRequests(
    private val client: GetAllMyRequestsClient
) {

    suspend fun execute(): Resource<List<Passenger>> = client.getAll()
}