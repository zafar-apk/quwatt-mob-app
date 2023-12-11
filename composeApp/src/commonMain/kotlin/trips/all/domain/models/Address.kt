package trips.all.domain.models

data class Address(
    val id: Int,
    val position: String,
    val userId: Int?,
    val price: Int
)
