package stations.detailed_trip.data.remote

import core.domain.util.Resource
import stations.all.domain.models.StationDetails

interface StationDetailsClient {
    suspend fun getStationById(id: Int): Resource<StationDetails>
}