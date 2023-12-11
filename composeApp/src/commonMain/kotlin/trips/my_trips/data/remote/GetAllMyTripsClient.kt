package tj.ham_safar.app.trips.my_trips.data.remote

import core.domain.util.Resource
import trips.all.domain.models.Trip

interface GetAllMyTripsClient {

    suspend fun getAll(): Resource<List<Trip>>
}