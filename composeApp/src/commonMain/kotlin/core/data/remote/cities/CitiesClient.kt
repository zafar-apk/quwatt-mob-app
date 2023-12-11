package core.data.remote.cities

import core.domain.util.Resource
import core.domain.cities.model.City

interface CitiesClient {
    suspend fun getCities(): Resource<List<City>>
}