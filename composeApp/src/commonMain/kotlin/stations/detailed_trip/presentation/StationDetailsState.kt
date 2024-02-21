package stations.detailed_trip.presentation

import stations.all.domain.models.Connector
import stations.all.domain.models.Station

data class StationDetailsState(
    val station: Station? = null,
    val connectors: List<Connector> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)