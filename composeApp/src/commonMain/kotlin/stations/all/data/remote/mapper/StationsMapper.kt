package stations.all.data.remote.mapper

import core.domain.cities.model.City
import stations.all.data.remote.model.StationsFilterBody
import stations.all.data.remote.model.TripsDTO
import stations.all.domain.models.Location
import stations.all.domain.models.Station
import stations.all.domain.models.Stations
import stations.all.domain.models.StationsFilter
import stations.all.domain.models.TimeOfDay

class StationsMapper {

    fun createStations(dto: TripsDTO): Stations = Stations(
        stations = dto.trips.map { creteStation() }
    )

    fun creteStation(): Station {
        return Station(
            id = 1,
            location = Location(
                latitude = 12.12,
                longitude = 10.14,
                city = City(
                    id = 1,
                    name = "Бустон"
                ),
                address = "Аэропорт"
            ),
            images = listOf(
                "https://spectrum.ieee.org/media-library/less-than-p-greater-than-electric-vehicle-charging-stations-in-monterey-park-calif-less-than-p-greater-than.jpg?id=32246419",
                "https://www.motorbiscuit.com/wp-content/uploads/2021/11/GettyImages-1235384443-1.jpg"
            ),
            workingHours = "24H",
            workingTimeOfDay = TimeOfDay.DAY_NIGHT,
            isFavorite = false,
            rateFrom = 1f,
            rateTo = 1.5f,
            powerFrom = 60,
            powerTo = 120,
            chargersCount = 2,
            status = "Available"
        )

    }

    fun createStationsFilterBody(query: StationsFilter): StationsFilterBody = StationsFilterBody(
        autoType = query.autoType,
        autoModel = query.autoModel,
        fromPriceTrip = query.fromPriceTrip,
        toPriceTrip = query.toPriceTrip,
        fromCityId = query.fromCity?.id,
        toCityId = query.toCity?.id,
        tripDate = query.tripDate,
        tripTime = query.tripTime,
        tripRating = query.tripRating
    )
}