package trips.all.domain.models

import profile.domain.User

data class Trip(
    val id: Int,
    val addressFrom: String,
    val addressTo: String,
    val cityFrom: String,
    val cityTo: String,
    val date: String,
    val time: String,
    val driver: User,
    val status: String?,
    val seats: List<Seat>,
//    val passengers: List<User>,
//    val minPrice: Int,
//    val maxPrice: Int,
//    val availableSeatsCount: Int,
//    val rating: Double,
)
