package passengers.detail.domain

import core.domain.util.Resource
import passengers.all.domain.Passenger
import passengers.detail.data.remote.DetailedPassengerClient

class GetPassengerById(
    private val detailedPassengerClient: DetailedPassengerClient
) {
    suspend fun execute(id: Int): Resource<Passenger> = detailedPassengerClient.getPassengerById(id)
}