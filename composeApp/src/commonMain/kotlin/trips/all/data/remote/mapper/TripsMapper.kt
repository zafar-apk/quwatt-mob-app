package trips.all.data.remote.mapper

import auth.enter_code.data.remote.model.toUser
import core.data.remote.cities.mapper.toCity
import trips.all.domain.models.BookedTrip
import trips.all.domain.models.Trip
import trips.all.domain.models.Trips
import trips.all.domain.models.TripsFilter
import trips.all.data.remote.model.BookedTripDTO
import trips.all.data.remote.model.TripDTO
import trips.all.data.remote.model.TripFilterBody
import trips.all.data.remote.model.TripsDTO
import trips.all.data.remote.model.toSeat

class TripsMapper {

    fun createTrips(dto: TripsDTO): Trips = Trips(
        isDriver = dto.isDriver,
        bookedTrips = dto.bookedTrips.map(::createBookedTrip),
        trips = dto.trips.map(::createTrip)
    )

    fun createTrip(dto: TripDTO): Trip = with(dto) {
        Trip(
            id = id,
            addressFrom = fromAddress,
            addressTo = toAddress,
            cityFrom = cityFrom.toCity(),
            cityTo = cityTo.toCity(),
            date = date,
            time = time,
            driver = driver.toUser(),
            status = status,
            seats = seats.map { it.toSeat() },
            passengers = passengers.map { it.toUser() },
            minPrice = minPrice,
            maxPrice = maxPrice,
            availableSeatsCount = availableSeatsCount,
            rating = rating,
        )

    }

    fun createBookedTrip(dto: BookedTripDTO): BookedTrip = with(dto) {
        BookedTrip(
            id = id,
            addressFrom = fromAddress,
            addressTo = toAddress,
            cityFrom = cityFrom.toCity(),
            cityTo = cityTo.toCity(),
            date = date,
            time = time,
            driver = driver.toUser(),
            status = status,
            seats = seats.map { it.toSeat() },
            passengers = passengers.map { it.toUser() },
            price = price,
            availableSeatsCount = availableSeatsCount,
            rating = rating,
        )

    }

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