package tj.ham_safar.app.trips.my_trips.domain

import core.domain.util.Resource
import trips.all.domain.models.Trip
import tj.ham_safar.app.trips.my_trips.data.remote.GetAllMyTripsClient

class GetAllMyTrips(
    private val client: GetAllMyTripsClient
) {

    suspend fun execute(): Resource<List<Trip>> = client.getAll()
}