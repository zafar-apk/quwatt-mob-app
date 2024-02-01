package stations.all.domain.use_case

import core.domain.util.Resource
import stations.all.data.remote.AllTripsClient
import stations.all.data.remote.mapper.StationsMapper
import stations.all.domain.models.Station
import stations.all.domain.models.StationsFilter

class GetAllStations(
    private val allTripsClient: AllTripsClient,
    private val mapper: StationsMapper
) {
    suspend fun execute(query: StationsFilter?): Resource<List<Station>> =
        Resource.Success(
            listOf(mapper.creteStation())
        )
//        allTripsClient.getTrips(query)
}