package tj.ham_safar.app.trips.core.create_trip.domain.usecases

import tj.ham_safar.app.trips.core.create_trip.domain.CreateTripRepository
import core.domain.util.Resource

class CreateTripUseCase(
    private val createTripRepository: CreateTripRepository
) {
    suspend operator fun invoke(): Resource<Unit> =
        createTripRepository.createTrip()

}