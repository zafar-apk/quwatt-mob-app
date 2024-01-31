package tj.ham_safar.app.trips.core.create_trip.domain.usecases


import tj.ham_safar.app.trips.core.create_trip.domain.CreateTripRepository
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripPricing

class SetTripPricingUseCase(
    private val createTripRepository: CreateTripRepository
) {
    operator fun invoke(tripPricing: TripPricing) {
        createTripRepository.tripPricing = tripPricing
    }
}

class GetTripPricingUseCase(
    private val createTripRepository: CreateTripRepository
) {
    operator fun invoke(): TripPricing? =
        createTripRepository.tripPricing
}