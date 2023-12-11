package passengers.all.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import passengers.all.data.mapper.toBody
import passengers.all.data.mapper.toPassenger
import passengers.all.data.model.PassengerDTO
import passengers.all.domain.Passenger
import passengers.all.domain.PassengerFilter

class GetAllPassengersHttpClient(
    private val client: HttpClient
) : GetAllPassengersClient {

    override suspend fun getAll(query: PassengerFilter?): Resource<List<Passenger>> =
        networkCall(
            map = ::mapAllPassengersResult,
            call = {
                client.post {
                    url("${AppConstants.BASE_URL}/passengers")
                    contentType(ContentType.Application.Json)
                    setBody(query?.toBody())
                }
            }
        )

    private fun mapAllPassengersResult(dto: List<PassengerDTO>): List<Passenger> =
        dto.map(PassengerDTO::toPassenger)
}