package passengers.my_requests.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import passengers.all.data.mapper.toPassenger
import passengers.all.data.model.PassengerDTO
import passengers.all.domain.Passenger

class GetAllMyRequestsHttpClient(
    private val client: HttpClient
) : GetAllMyRequestsClient {

    override suspend fun getAll(): Resource<List<Passenger>> = networkCall(
        map = ::mapAllPassengersResult,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/my-requests")
                contentType(ContentType.Application.Json)
            }
        }
    )

    private fun mapAllPassengersResult(dto: List<PassengerDTO>): List<Passenger> =
        dto.map(PassengerDTO::toPassenger)
}