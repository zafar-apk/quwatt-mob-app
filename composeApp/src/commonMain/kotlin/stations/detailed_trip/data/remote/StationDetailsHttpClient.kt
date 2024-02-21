package stations.detailed_trip.data.remote

import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import stations.all.data.remote.mapper.StationsMapper
import stations.all.domain.models.StationDetails

class StationDetailsHttpClient(
    private val client: HttpClient,
    private val stationsMapper: StationsMapper
) : StationDetailsClient {

    override suspend fun getStationById(id: Int): Resource<StationDetails> = networkCall(
        map = stationsMapper::createStationDetails,
        call = {
            client.get ("${AppConstants.BASE_URL}/station/$id"){
                contentType(ContentType.Application.Json)
            }
        }
    )
}