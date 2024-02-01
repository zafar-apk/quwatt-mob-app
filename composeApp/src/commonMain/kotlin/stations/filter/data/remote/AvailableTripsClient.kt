package stations.filter.data.remote

import core.domain.util.Resource
import tj.ham_safar.app.trips.filter.domain.models.AvailableTrips
import stations.all.domain.models.StationsFilter

interface AvailableTripsClient {
    suspend fun getAvailableTrips(query: StationsFilter?): Resource<AvailableTrips>
}