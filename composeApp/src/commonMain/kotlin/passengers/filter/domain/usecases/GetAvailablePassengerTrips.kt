package passengers.filter.domain.usecases

import core.domain.util.Resource
import passengers.all.domain.PassengerFilter
import passengers.filter.data.remote.AvailablePassengerTripsClient
import passengers.filter.domain.models.AvailablePassengerTrips

class GetAvailablePassengerTrips(
    private val client: AvailablePassengerTripsClient
) {
    suspend operator fun invoke(filter: PassengerFilter?): Resource<AvailablePassengerTrips> =
        client.getAvailableTrips(filter)
}