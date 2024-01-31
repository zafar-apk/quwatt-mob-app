package stations.all.domain.models

data class Station(
    val id: Int,
    val location: Location,
    val images: List<String>,
    val workingHours: String,
    val workingTimeOfDay: TimeOfDay,
    val isFavorite: Boolean,
    val rateFrom: Float,
    val rateTo: Float?,
    val powerFrom: Int,
    val powerTo: Int?,
    val chargersCount: Int,
    val status: String
)