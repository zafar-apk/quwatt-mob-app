package passengers.filter.data.remote

import core.domain.util.Resource
import passengers.all.domain.PassengerFilter
import passengers.filter.domain.models.AvailablePassengerTrips

interface AvailablePassengerTripsClient {
    suspend fun getAvailableTrips(query: PassengerFilter?): Resource<AvailablePassengerTrips>
}