package trips.all.domain.models

data class Addresses(
    val id: Int,
    val fromAddress: Address,
    val toAddress: Address
)