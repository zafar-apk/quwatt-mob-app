package passengers.all.domain

import profile.domain.User
import core.domain.cities.model.City

data class Passenger(
    val id: Int,
    val cityFrom: City,
    val cityTo: City,
    val addressFrom: String,
    val addressTo: String,
    val count: Int,
    val priceFrom: Int,
    val priceTo: Int,
    val user: User,
    val date: String,
    val time: String,
    val rating: Double,
    val driverId: Int,
    val requests: List<User>
)