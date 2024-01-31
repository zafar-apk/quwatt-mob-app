package tj.ham_safar.app.trips.my_trips.domain

import core.domain.util.Resource
import stations.all.domain.models.Station
import tj.ham_safar.app.trips.my_trips.data.remote.GetAllMyTripsClient

class GetAllMyTrips(
    private val client: GetAllMyTripsClient
) {

    suspend fun execute(): Resource<List<Station>> = client.getAll()
}