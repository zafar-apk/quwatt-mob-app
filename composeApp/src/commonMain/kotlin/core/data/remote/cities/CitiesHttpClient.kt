package core.data.remote.cities

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.cities.model.CityDTO
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import core.domain.cities.model.City

class CitiesHttpClient(
    private val client: HttpClient
) : CitiesClient {
    override suspend fun getCities(): Resource<List<City>> = networkCall(
        map = ::mapCitiesResult,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/cities")
                contentType(ContentType.Application.Json)
            }
        }
    )

    private fun mapCitiesResult(dto: List<CityDTO>): List<City> = dto.map {
        City(
            id = it.id,
            name = it.name,
        )
    }
}