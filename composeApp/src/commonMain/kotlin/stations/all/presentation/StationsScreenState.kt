package stations.all.presentation

import stations.all.domain.models.Station
import ui.stations.core.models.StationsFilter

data class StationsScreenState(
    val stations: List<Station> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val stationDetail: Int? = null,
    val shouldRequestAuthorization: Boolean = false,
    val shouldRegisterUserInfo: Boolean = false,
    val stationsFilter: StationsFilter? = null
)