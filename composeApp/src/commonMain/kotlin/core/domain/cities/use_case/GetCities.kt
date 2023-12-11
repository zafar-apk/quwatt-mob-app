package core.domain.cities.use_case

import core.data.remote.cities.CitiesClient
import core.domain.util.Resource
import core.domain.cities.model.City

class GetCities(
    private val citiesClient: CitiesClient
) {
    suspend fun execute(): Resource<List<City>> = citiesClient.getCities()
}