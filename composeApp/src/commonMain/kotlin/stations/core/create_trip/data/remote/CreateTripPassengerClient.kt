package stations.core.create_trip.data.remote

import core.domain.util.Resource
import stations.core.create_trip.data.remote.model.CreateTripPassengerRequest

interface CreateTripPassengerClient {

    suspend fun createTrip(request: CreateTripPassengerRequest): Resource<Unit>

}