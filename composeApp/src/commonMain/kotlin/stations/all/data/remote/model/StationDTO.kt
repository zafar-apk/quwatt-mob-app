package stations.all.data.remote.model

import core.data.remote.LocationDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import stations.all.domain.models.TimeOfDay

@Serializable
class StationDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("location")
    val location: LocationDTO,
    @SerialName("images")
    val images: List<String>,
    @SerialName("workingHours")
    val workingHours: String,
    @SerialName("workingTimeOfDay")
    val workingTimeOfDay: TimeOfDay,
    @SerialName("isFavorite")
    val isFavorite: Boolean,
    @SerialName("rateFrom")
    val rateFrom: Float,
    @SerialName("rateTo")
    val rateTo: Float?,
    @SerialName("powerFrom")
    val powerFrom: Int,
    @SerialName("powerTo")
    val powerTo: Int?,
    @SerialName("chargersCount")
    val chargersCount: Int,
    @SerialName("isAvailable")
    val isAvailable: Boolean
)