package stations.filter.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import stations.all.data.remote.mapper.TripsMapper
import stations.all.domain.models.TripsFilter
import tj.ham_safar.app.trips.filter.domain.models.AvailableTrips

class AvailableTripsHttpClient(
    private val client: HttpClient,
    private val mapper: TripsMapper
) : AvailableTripsClient {

    override suspend fun getAvailableTrips(query: TripsFilter?): Resource<AvailableTrips> =
        networkCall(
            map = { count: Int -> AvailableTrips(count) },
            call = {
                client.post {
                    url("${AppConstants.BASE_URL}/trips-count")
                    contentType(ContentType.Application.Json)
                    setBody(query?.let { mapper.createTripFilterBody(it) })
                }
            }
        )
}