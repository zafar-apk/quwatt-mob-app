package trips.core.create_trip.data.mapper

import trips.all.data.remote.model.SeatDTO
import trips.core.create_trip.data.remote.model.CreateTripDriverRequest
import trips.core.create_trip.data.remote.model.CreateTripPassengerRequest
import trips.core.create_trip.domain.models.TripDateTime
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripLocation
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripPricing


fun mapPassengerTripDataToRequest(
    tripLocation: TripLocation,
    tripDateTime: TripDateTime,
    tripPricing: TripPricing.TripPassengerPricing
) =
    CreateTripPassengerRequest(
        cityFrom = tripLocation.cityFrom.id,
        cityTo = tripLocation.cityTo.id,
        addressFrom = tripLocation.addressFrom,
        addressTo = tripLocation.addressTo,
        count = tripPricing.passengerCount,
        priceFrom = tripPricing.fromPrice,
        priceTo = tripPricing.toPrice,
        date = tripDateTime.date,
        time = tripDateTime.time
    )


fun mapDriverTripDataToRequest(
    tripLocation: TripLocation,
    tripDateTime: TripDateTime,
    tripPricing: TripPricing.TripDriverPricing
): CreateTripDriverRequest {

    val request = CreateTripDriverRequest(
        cityFrom = tripLocation.cityFrom.id,
        cityTo = tripLocation.cityTo.id,
        addressFrom = tripLocation.addressFrom,
        addressTo = tripLocation.addressTo,
        date = tripDateTime.date,
        time = tripDateTime.time,
        seats = tripPricing.seats.map {
            SeatDTO(
                id = it.id,
                position = it.position,
                userId = it.userId,
                price = it.price,
                isAvailable = true
            )
        }
    )
    return request

}


