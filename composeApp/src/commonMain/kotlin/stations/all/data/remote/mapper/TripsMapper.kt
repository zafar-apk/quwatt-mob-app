package stations.all.data.remote.mapper

import core.domain.cities.model.City
import stations.all.data.remote.model.TripFilterBody
import stations.all.data.remote.model.TripsDTO
import stations.all.domain.models.Location
import stations.all.domain.models.Station
import stations.all.domain.models.TimeOfDay
import stations.all.domain.models.Trips
import stations.all.domain.models.TripsFilter

class TripsMapper {

    fun createTrips(dto: TripsDTO): Trips = Trips(
//        isDriver = dto.isDriver,
        stations = dto.trips.map { createTrip() }
    )

    fun createTrip(): Station {
        return Station(
            id = 1,
            location = Location(
                latitude = 12.12,
                longitude = 10.14,
                city = City(
                    id = 1,
                    name = "Dushanbe"
                ),
                address = "Some address"
            ),
            images = listOf("https://picsum.photos/200/300"),
            workingHours = "24H",
            workingTimeOfDay = TimeOfDay.DAY_NIGHT,
            isFavorite = false,
            rateFrom = 4.5f,
            rateTo = 5.0f,
            powerFrom = 10,
            powerTo = 20,
            chargersCount = 2,
            status = "Busy"
        )

    }

//    fun createBookedTrip(dto: BookedTripDTO): BookedTrip = with(dto) {
//        BookedTrip(
//            id = id,
//            addressFrom = fromAddress,
//            addressTo = toAddress,
//            cityFrom = cityFrom.toCity(),
//            cityTo = cityTo.toCity(),
//            date = date,
//            time = time,
//            driver = driver.toUser(),
//            status = status,
//            seats = seats.map { it.toSeat() },
//            passengers = passengers.map { it.toUser() },
//            price = price,
//            availableSeatsCount = availableSeatsCount,
//            rating = rating,
//        )
//
//    }

    fun createTripFilterBody(query: TripsFilter): TripFilterBody = TripFilterBody(
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