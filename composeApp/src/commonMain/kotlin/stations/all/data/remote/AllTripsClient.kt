package stations.all.data.remote

import core.domain.util.Resource
import stations.all.domain.models.StationsFilter
import stations.all.domain.models.Stations

interface AllTripsClient {
    suspend fun getTrips(query: StationsFilter?): Resource<Stations>
}