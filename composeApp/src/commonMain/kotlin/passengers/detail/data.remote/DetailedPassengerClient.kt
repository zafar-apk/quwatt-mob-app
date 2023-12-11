package passengers.detail.data.remote

import core.domain.util.Resource
import passengers.all.domain.Passenger

interface DetailedPassengerClient {
    suspend fun getPassengerById(id: Int): Resource<Passenger>
}