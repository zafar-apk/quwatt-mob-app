package trips.all.data.remote

import core.domain.util.Resource
import trips.all.domain.models.TripsFilter
import trips.all.domain.models.Trips

interface AllTripsClient {
    suspend fun getTrips(query: TripsFilter?): Resource<Trips>
}