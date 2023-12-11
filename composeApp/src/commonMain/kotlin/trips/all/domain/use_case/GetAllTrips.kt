package trips.all.domain.use_case

import core.domain.util.Resource
import trips.all.data.remote.AllTripsClient
import trips.all.domain.models.TripsFilter
import trips.all.domain.models.Trips

class GetAllTrips(
    private val allTripsClient: AllTripsClient
) {
    suspend fun execute(query: TripsFilter?): Resource<Trips> =
        allTripsClient.getTrips(query)
}