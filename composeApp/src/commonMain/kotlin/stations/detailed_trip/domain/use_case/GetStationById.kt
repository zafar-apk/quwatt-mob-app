package stations.detailed_trip.domain.use_case

import core.domain.util.Resource
import stations.all.domain.models.StationDetails
import stations.detailed_trip.data.remote.StationDetailsClient

class GetStationById(
    private val stationDetailsClient: StationDetailsClient
) {
    suspend fun execute(id: Int): Resource<StationDetails> = stationDetailsClient.getStationById(id)
}