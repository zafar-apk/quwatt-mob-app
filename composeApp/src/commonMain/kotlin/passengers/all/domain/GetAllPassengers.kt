package passengers.all.domain

import core.domain.util.Resource
import passengers.all.data.remote.GetAllPassengersClient

class GetAllPassengers(
    private val client: GetAllPassengersClient
) {

    suspend fun execute(query: PassengerFilter?): Resource<List<Passenger>> =
        client.getAll(query)
}