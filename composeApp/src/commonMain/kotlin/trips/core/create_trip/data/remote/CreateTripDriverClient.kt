package trips.core.create_trip.data.remote

import core.domain.util.Resource
import trips.core.create_trip.data.remote.model.CreateTripDriverRequest

interface CreateTripDriverClient {

    suspend fun createTrip(request: CreateTripDriverRequest): Resource<Unit>

}