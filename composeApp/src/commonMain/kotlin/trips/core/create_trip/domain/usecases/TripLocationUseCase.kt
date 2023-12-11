package tj.ham_safar.app.trips.core.create_trip.domain.usecases

import tj.ham_safar.app.trips.core.create_trip.domain.CreateTripRepository
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripLocation

class SetTripLocationUseCase(
    private val createTripRepository: CreateTripRepository
) {
    operator fun invoke(tripLocation: TripLocation) {
        createTripRepository.tripLocation = tripLocation
    }
}

class GetTripLocationUseCase(
    private val createTripRepository: CreateTripRepository
) {
    operator fun invoke(): TripLocation? =
        createTripRepository.tripLocation
}