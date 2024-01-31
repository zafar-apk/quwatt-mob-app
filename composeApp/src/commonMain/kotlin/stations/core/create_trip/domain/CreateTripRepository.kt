package tj.ham_safar.app.trips.core.create_trip.domain

import stations.core.create_trip.domain.models.TripDateTime
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripLocation
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripPricing
import core.domain.util.Resource

interface CreateTripRepository {

    var tripLocation: TripLocation?

    var tripDateTime: TripDateTime?

    var tripPricing: TripPricing?

    suspend fun createTrip(): Resource<Unit>

}