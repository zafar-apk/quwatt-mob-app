package stations.all.data.remote

import core.domain.util.Resource
import stations.all.domain.models.Station
import stations.all.domain.models.StationsFilter

interface AllStationsClient {
    suspend fun getStations(query: StationsFilter?): Resource<List<Station>>
}