package tj.ham_safar.app.trips.detailed_booking_trip.data.remote.mapper

import auth.enter_code.data.remote.model.toUser
import core.data.remote.cities.mapper.toCity
import trips.all.data.remote.model.toSeat
import trips.all.domain.models.BookedTrip
import trips.detailed_booking_trip.data.remote.model.DetailBookedTripDTO

class DetailedBookedTripMapper {

    fun createBookedTrip(dto: DetailBookedTripDTO): BookedTrip = with(dto) {
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
            price = maxPrice,
            availableSeatsCount = availableSeatsCount,
            rating = rating,
        )

    }

}