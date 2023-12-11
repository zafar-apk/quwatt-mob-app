package passengers.filter.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import passengers.all.data.mapper.toBody
import passengers.all.domain.PassengerFilter
import passengers.filter.domain.models.AvailablePassengerTrips

class AvailablePassengerTripsHttpClient(
    private val client: HttpClient,
) : AvailablePassengerTripsClient {

    override suspend fun getAvailableTrips(query: PassengerFilter?): Resource<AvailablePassengerTrips> =
        networkCall(
            map = { count: Int -> AvailablePassengerTrips(count) },
            call = {
                client.post {
                    url("${AppConstants.BASE_URL}/passengers-count")
                    contentType(ContentType.Application.Json)
                    setBody(query?.toBody())
                }
            }
        )
}