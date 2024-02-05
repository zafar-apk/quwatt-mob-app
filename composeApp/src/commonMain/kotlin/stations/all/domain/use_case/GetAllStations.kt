package stations.all.domain.use_case

import core.domain.util.Resource
import stations.all.data.remote.AllStationsClient
import stations.all.domain.models.Station
import stations.all.domain.models.StationsFilter

class GetAllStations(
    private val allStationsClient: AllStationsClient
) {

    suspend fun execute(query: StationsFilter?): Resource<List<Station>> =
        allStationsClient.getStations(query)
}