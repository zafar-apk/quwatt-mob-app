package stations.all.data.remote

import core.domain.util.Resource
import stations.all.domain.models.TripsFilter
import stations.all.domain.models.Trips

interface AllTripsClient {
    suspend fun getTrips(query: TripsFilter?): Resource<Trips>
}