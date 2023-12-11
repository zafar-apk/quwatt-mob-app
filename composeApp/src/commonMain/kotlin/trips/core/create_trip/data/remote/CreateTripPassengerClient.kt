package trips.core.create_trip.data.remote

import core.domain.util.Resource
import trips.core.create_trip.data.remote.model.CreateTripPassengerRequest

interface CreateTripPassengerClient {

    suspend fun createTrip(request: CreateTripPassengerRequest): Resource<Unit>

}