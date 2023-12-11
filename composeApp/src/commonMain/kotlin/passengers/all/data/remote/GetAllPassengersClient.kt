package passengers.all.data.remote

import core.domain.util.Resource
import passengers.all.domain.Passenger
import passengers.all.domain.PassengerFilter

interface GetAllPassengersClient {

    suspend fun getAll(query: PassengerFilter?): Resource<List<Passenger>>
}