package core.data.remote.cities.mapper

import core.data.remote.cities.model.CityDTO
import core.domain.cities.model.City

fun CityDTO.toCity() = City(id = id, name = name)

fun City.toCityDto() = CityDTO(id = id, name = name)
