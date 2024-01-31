package stations.all.presentation

import ui.stations.core.models.StationsFilter

sealed class StationsScreenEvent {
    data object OpenStationsHistory : StationsScreenEvent()
    data object OpenStationsMenu : StationsScreenEvent()
    data object ResetNavigation : StationsScreenEvent()
    data object NavigateToLoginScreen : StationsScreenEvent()
    data object NavigateToProfileScreen : StationsScreenEvent()
    data object LoadStations : StationsScreenEvent()
    data object CancelShowingAuthDialog : StationsScreenEvent()
    data class NavigateToChargingStation(val id: Int) : StationsScreenEvent()
    data class RequestToOpenChargingStation(val id: Int) : StationsScreenEvent()
    data class StationsFilterData(val filter: StationsFilter?) : StationsScreenEvent()
}