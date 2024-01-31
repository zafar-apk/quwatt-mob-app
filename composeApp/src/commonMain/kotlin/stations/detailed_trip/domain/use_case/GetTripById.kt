package tj.ham_safar.app.trips.detailed_trip.domain.use_case

import core.domain.util.Resource
import stations.all.domain.models.Station
import tj.ham_safar.app.trips.detailed_trip.data.remote.DetailedTripClient

class GetTripById(
    private val detailedTripClient: DetailedTripClient
) {
    suspend fun execute(id: Int): Resource<Station> = detailedTripClient.getTripById(id)
}