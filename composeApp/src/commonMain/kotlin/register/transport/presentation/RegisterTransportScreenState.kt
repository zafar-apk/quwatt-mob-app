package register.transport.presentation

import core.domain.util.ImageFile
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType

data class RegisterTransportScreenState(
    val photo: ImageFile? = null,
    val type: TransportType = TransportType.SEDAN,
    val typeIsNotSelected: Boolean = false,
    val isPickingType: Boolean = false,
    val brand: TransportBrand = TransportBrand.OPEL,
    val brandIsNotSelected: Boolean = false,
    val isPickingBrand: Boolean = false,
    val model: String? = null,
    val modelIsNotEntered: Boolean = false,
    val color: TransportColors = TransportColors.SILVER,
    val colorIsNotPicked: Boolean = false,
    val isPickingColor: Boolean = false,
    val yearOfRelease: Int? = null,
    val yearIfIssueIsNotEntered: Boolean = false,
    val capacity: Int? = null,
    val capacityIsNotPicked: Boolean = false,
    val hasAC: Boolean = false,
    val isTransportRegistered: Boolean = false,
    val error: Throwable? = null,
    val isLoading: Boolean = false
)