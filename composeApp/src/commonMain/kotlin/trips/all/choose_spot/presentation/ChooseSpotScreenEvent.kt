package trips.all.choose_spot.presentation


sealed class ChooseSpotScreenEvent {
    object OnBackClick : ChooseSpotScreenEvent()
    object OnNextClick : ChooseSpotScreenEvent()
    object ChooseMoreSpot : ChooseSpotScreenEvent()
    data class LoadTrip(val tripId: Int) : ChooseSpotScreenEvent()


}