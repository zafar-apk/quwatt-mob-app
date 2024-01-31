package stations.all.domain.use_case

import core.domain.util.Resource
import stations.all.data.remote.AllTripsClient
import stations.all.data.remote.mapper.TripsMapper
import stations.all.domain.models.Station
import stations.all.domain.models.TripsFilter

class GetAllStations(
    private val allTripsClient: AllTripsClient,
    private val mapper: TripsMapper
) {
    suspend fun execute(query: TripsFilter?): Resource<List<Station>> =
        Resource.Success(
            listOf(mapper.createTrip())
        )
//        allTripsClient.getTrips(query)
}