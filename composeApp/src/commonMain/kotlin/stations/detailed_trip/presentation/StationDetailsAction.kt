package stations.detailed_trip.presentation

sealed interface StationDetailsAction{
    data object OnBackClick : StationDetailsAction

}