package tj.ham_safar.app.trips.core.create_trip.domain.usecases

import tj.ham_safar.app.trips.core.create_trip.domain.CreateTripRepository
import stations.core.create_trip.domain.models.TripDateTime

class SetTripDateTimeUseCase(
    private val createTripRepository: CreateTripRepository
) {
    operator fun invoke(tripDateTime: TripDateTime) {
        createTripRepository.tripDateTime = tripDateTime
    }
}

class GetTripDateTimeUseCase(
    private val createTripRepository: CreateTripRepository
) {
    operator fun invoke(): TripDateTime? =
        createTripRepository.tripDateTime
}