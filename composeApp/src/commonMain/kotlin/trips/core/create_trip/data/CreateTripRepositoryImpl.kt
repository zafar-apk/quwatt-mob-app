package trips.core.create_trip.data

import core.domain.util.Resource
import trips.core.create_trip.data.mapper.mapDriverTripDataToRequest
import trips.core.create_trip.data.mapper.mapPassengerTripDataToRequest
import trips.core.create_trip.data.remote.CreateTripDriverClient
import trips.core.create_trip.data.remote.CreateTripPassengerClient
import tj.ham_safar.app.trips.core.create_trip.domain.CreateTripRepository
import trips.core.create_trip.domain.errors.TripCreationError
import trips.core.create_trip.domain.models.TripDateTime
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripLocation
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripPricing

class CreateTripRepositoryImpl(
    private val driverClient: CreateTripDriverClient,
    private val passengerClient: CreateTripPassengerClient
) : CreateTripRepository {

    override var tripLocation: TripLocation? = null

    override var tripDateTime: TripDateTime? = null

    override var tripPricing: TripPricing? = null

    override suspend fun createTrip(): Resource<Unit> {
        val tripPricing = this.tripPricing
        val tripLocation = this.tripLocation
        val tripDateTime = this.tripDateTime
        //the order of the null checks matters.
        if (tripLocation == null) return Resource.Error(TripCreationError.LocationMissingError())
        if (tripDateTime == null) return Resource.Error(TripCreationError.DateTimeMissingError())
        if (tripPricing == null) return Resource.Error(TripCreationError.PricingMissingError())

        val resource = when (tripPricing) {
            is TripPricing.TripPassengerPricing -> passengerClient.createTrip(
                mapPassengerTripDataToRequest(tripLocation, tripDateTime, tripPricing)
            )
            is TripPricing.TripDriverPricing -> driverClient.createTrip(
                mapDriverTripDataToRequest(tripLocation, tripDateTime, tripPricing)
            )
        }

        if (resource is Resource.Success) clearData()
         return resource
    }

    private fun clearData() {
        tripLocation = null
        tripDateTime = null
        tripPricing = null
    }

}

