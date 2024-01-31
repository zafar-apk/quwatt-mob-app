package tj.ham_safar.app.trips.my_trips.data.remote

import core.domain.util.Resource
import stations.all.domain.models.Station

interface GetAllMyTripsClient {

    suspend fun getAll(): Resource<List<Station>>
}