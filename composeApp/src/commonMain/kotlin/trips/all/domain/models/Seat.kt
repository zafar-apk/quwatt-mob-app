package trips.all.domain.models

data class Seat(
    val id: Int,
    val position: String,
    val isAvailable: Boolean,
    val price: Int
)