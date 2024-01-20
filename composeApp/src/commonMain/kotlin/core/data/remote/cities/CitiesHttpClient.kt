package core.data.remote.cities

import core.data.remote.cities.model.CitiesDTO
import core.data.remote.networkCall
import core.domain.cities.model.City
import core.domain.util.AppConstants
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CitiesHttpClient(
    private val client: HttpClient
) : CitiesClient {
    override suspend fun getCities(): Resource<List<City>> = networkCall(
        map = ::mapCitiesResult,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/regions")
                contentType(ContentType.Application.Json)
            }
        }
    )

    private fun mapCitiesResult(dto: CitiesDTO): List<City> = dto.result?.map {
        City(
            id = it.id,
            name = it.name,
        )
    }.orEmpty()
}