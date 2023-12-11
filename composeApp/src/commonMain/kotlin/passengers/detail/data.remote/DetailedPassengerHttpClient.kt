package passengers.detail.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import passengers.all.data.mapper.toPassenger
import passengers.all.data.model.PassengerDTO
import passengers.all.domain.Passenger
import passengers.detail.data.remote.DetailedPassengerClient

class DetailedPassengerHttpClient(
    private val client: HttpClient
) : DetailedPassengerClient {
    override suspend fun getPassengerById(id: Int): Resource<Passenger> = networkCall(
        map = ::mapPassengerResult,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/passenger/$id")
                contentType(ContentType.Application.Json)
            }
        }
    )

    private fun mapPassengerResult(dto: PassengerDTO): Passenger = dto.toPassenger()

}