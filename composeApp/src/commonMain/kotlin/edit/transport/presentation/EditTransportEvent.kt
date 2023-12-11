package edit.transport.presentation

import core.domain.util.ImageFile
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType

sealed class EditTransportEvent {
    data class OnTransportPhotoPicked(val photo: ImageFile) : EditTransportEvent()
    data class OnTransportTypePicked(val transportType: TransportType) : EditTransportEvent()
    object OnPickTransportType : EditTransportEvent()
    data class OnTransportBrandPicked(val brand: TransportBrand) : EditTransportEvent()
    object OnPickTransportBrand : EditTransportEvent()
    data class OnTransportModelChanged(val model: String) : EditTransportEvent()
    data class OnTransportColorPicked(val color: TransportColors) : EditTransportEvent()
    object OnPickTransportColor : EditTransportEvent()
    data class OnDateOfIssueChanged(val year: Int) : EditTransportEvent()
    data class OnCapacityPicked(val capacity: Int?) : EditTransportEvent()
    data class OnHasACPicked(val hasAC: Boolean) : EditTransportEvent()
    object Edit : EditTransportEvent()
    object GoBack : EditTransportEvent()
    object ResetState : EditTransportEvent()
    object OnCancelPickingTransportType : EditTransportEvent()
    object OnCancelPickingTransportBrand : EditTransportEvent()
    object OnCancelPickingTransportColor : EditTransportEvent()
}