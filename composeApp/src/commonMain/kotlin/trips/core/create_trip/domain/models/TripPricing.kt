package tj.ham_safar.app.trips.core.create_trip.domain.models

import trips.all.domain.models.Seat

sealed class TripPricing {

    data class TripPassengerPricing(
        val passengerCount: Int,
        val fromPrice: Int,
        val toPrice: Int
    ) : TripPricing()

    data class TripDriverPricing(
        val seats: List<Seat>,
        val price: Int
    ) : TripPricing()
}