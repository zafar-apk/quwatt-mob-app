package stations.detailed_trip.presentation

sealed interface StationDetailsEvent {
    data class LoadStationDetails(val id: Int) : StationDetailsEvent
}