package trips.all.domain.models

import profile.domain.User
import core.domain.cities.model.City
import trips.all.domain.models.Seat

data class Trip(
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
    val minPrice: Int,
    val maxPrice: Int,
    val availableSeatsCount: Int,
    val rating: Double,
)
