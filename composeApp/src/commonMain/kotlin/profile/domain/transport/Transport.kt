package profile.domain.transport

data class Transport(
    val id: Int,
    val photo: String?,
    val type: TransportType,
    val brand: TransportBrand,
    val model: String,
    val colors: TransportColors,
    val capacity: Int,
    val dateOfIssue: String,
    val hasConditioner: Boolean
)
