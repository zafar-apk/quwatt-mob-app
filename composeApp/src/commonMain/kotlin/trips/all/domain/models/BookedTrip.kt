package trips.all.domain.models

import core.domain.cities.model.City
import profile.domain.User

data class BookedTrip(
    val id: Int,
    val addressFrom: String,
    val addressTo: String,
    val cityFrom: City,
    val cityTo: City,
    val date: String,
    val time: String,
    val driver: User,
    val status: String,
    val seats: List<Seat>,
    val passengers: List<User>,
    val price: Int,
    val availableSeatsCount: Int,
    val rating: Double,
)
