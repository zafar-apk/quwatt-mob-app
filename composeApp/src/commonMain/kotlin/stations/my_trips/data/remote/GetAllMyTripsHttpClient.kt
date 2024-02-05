package stations.my_trips.data.remote

import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import stations.all.data.remote.mapper.StationsMapper
import stations.all.data.remote.model.StationDTO
import stations.all.domain.models.Station
import tj.ham_safar.app.trips.my_trips.data.remote.GetAllMyTripsClient

class GetAllMyTripsHttpClient(
    private val client: HttpClient,
    private val mapper: StationsMapper
) : GetAllMyTripsClient {

    override suspend fun getAll(): Resource<List<Station>> = networkCall(
        map = ::mapAllTripsResult,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/my-trips")
                contentType(ContentType.Application.Json)
            }
        }
    )

    private fun mapAllTripsResult(dto: List<StationDTO>): List<Station> =
        dto.map { mapper.createStation(it) }
}