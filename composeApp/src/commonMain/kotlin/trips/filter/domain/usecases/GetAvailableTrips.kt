package tj.ham_safar.app.trips.filter.domain.usecases

import core.domain.util.Resource
import trips.all.domain.models.TripsFilter
import trips.filter.data.remote.AvailableTripsClient
import tj.ham_safar.app.trips.filter.domain.models.AvailableTrips

class GetAvailableTrips(
    private val client: AvailableTripsClient
) {
    suspend operator fun invoke(query: TripsFilter?): Resource<AvailableTrips> =
        client.getAvailableTrips(query)
}