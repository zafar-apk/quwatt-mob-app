package register.transport.presentation

import core.domain.util.ImageFile
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType

sealed class RegisterTransportEvent {
    data class OnTransportPhotoPicked(val photo: ImageFile) : RegisterTransportEvent()
    data class OnTransportTypePicked(val transportType: TransportType) : RegisterTransportEvent()
    object OnPickTransportType : RegisterTransportEvent()
    data class OnTransportBrandPicked(val brand: TransportBrand) : RegisterTransportEvent()
    object OnPickTransportBrand : RegisterTransportEvent()
    data class OnTransportModelChanged(val model: String) : RegisterTransportEvent()
    data class OnTransportColorPicked(val color: TransportColors) : RegisterTransportEvent()
    object OnPickTransportColor : RegisterTransportEvent()
    data class OnDateOfIssueChanged(val year: Int) : RegisterTransportEvent()
    data class OnCapacityPicked(val capacity: Int?) : RegisterTransportEvent()
    data class OnHasACPicked(val hasAC: Boolean) : RegisterTransportEvent()
    object Save : RegisterTransportEvent()
    object GoBack : RegisterTransportEvent()
    object ResetState : RegisterTransportEvent()
    object OnCancelPickingTransportType : RegisterTransportEvent()
    object OnCancelPickingTransportBrand : RegisterTransportEvent()
    object OnCancelPickingTransportColor : RegisterTransportEvent()
    object NavigateToProfile : RegisterTransportEvent()
}