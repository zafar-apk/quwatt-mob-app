package stations.core.create_trip.data.remote

import core.domain.util.Resource
import stations.core.create_trip.data.remote.model.CreateTripDriverRequest

interface CreateTripDriverClient {

    suspend fun createTrip(request: CreateTripDriverRequest): Resource<Unit>

}