package stations.core.create_trip.data.mapper

import tj.ham_safar.app.trips.core.create_trip.domain.models.TripLocation
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripPricing
import stations.all.data.remote.model.SeatDTO
import stations.core.create_trip.data.remote.model.CreateTripDriverRequest
import stations.core.create_trip.data.remote.model.CreateTripPassengerRequest
import stations.core.create_trip.domain.models.TripDateTime


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
                price = it.price,
                isAvailable = 1
            )
        }
    )
    return request

}


