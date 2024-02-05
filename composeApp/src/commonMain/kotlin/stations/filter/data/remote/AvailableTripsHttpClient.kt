package stations.filter.data.remote

import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import stations.all.data.remote.mapper.StationsMapper
import stations.all.domain.models.StationsFilter
import tj.ham_safar.app.trips.filter.domain.models.AvailableTrips

class AvailableTripsHttpClient(
    private val client: HttpClient,
    private val mapper: StationsMapper
) : AvailableTripsClient {

    override suspend fun getAvailableTrips(query: StationsFilter?): Resource<AvailableTrips> =
        networkCall(
            map = { count: Int -> AvailableTrips(count) },
            call = {
                client.post {
                    url("${AppConstants.BASE_URL}/trips-count")
                    contentType(ContentType.Application.Json)
                    setBody(query?.let(mapper::createStationsFilterBody))
                }
            }
        )
}