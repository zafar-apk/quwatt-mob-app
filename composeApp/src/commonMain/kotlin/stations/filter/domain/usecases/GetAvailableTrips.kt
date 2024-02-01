package tj.ham_safar.app.trips.filter.domain.usecases

import core.domain.util.Resource
import stations.all.domain.models.StationsFilter
import stations.filter.data.remote.AvailableTripsClient
import tj.ham_safar.app.trips.filter.domain.models.AvailableTrips

class GetAvailableTrips(
    private val client: AvailableTripsClient
) {
    suspend operator fun invoke(query: StationsFilter?): Resource<AvailableTrips> =
        client.getAvailableTrips(query)
}