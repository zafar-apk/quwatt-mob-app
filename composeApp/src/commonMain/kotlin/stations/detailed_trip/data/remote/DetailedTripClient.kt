package tj.ham_safar.app.trips.detailed_trip.data.remote

import core.domain.util.Resource
import stations.all.domain.models.Station

interface DetailedTripClient {
    suspend fun getTripById(id: Int): Resource<Station>
}