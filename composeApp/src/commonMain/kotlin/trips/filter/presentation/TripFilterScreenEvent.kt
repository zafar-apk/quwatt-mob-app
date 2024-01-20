package trips.filter.presentation

import core.domain.cities.model.City

sealed class TripFilterScreenEvent {

    object Submit : TripFilterScreenEvent()
    object ResetFilter : TripFilterScreenEvent()
    object BackPress : TripFilterScreenEvent()
    data class GoBackWithResult<T>(val filterResult: T) : TripFilterScreenEvent()
    object OpenAutoTypeDropDown : TripFilterScreenEvent()
    object OpenAutoModelDropDown : TripFilterScreenEvent()
    object OpenToCityDropDown : TripFilterScreenEvent()
    object OpenFromCityDropDown : TripFilterScreenEvent()
    object OpenTripDateCalendar : TripFilterScreenEvent()
    object OpenTripTimeDialog : TripFilterScreenEvent()
    object OpenTripRatingDropDown : TripFilterScreenEvent()
    object DismissAllChoosing : TripFilterScreenEvent()
    object OnPriceValuesReseted : TripFilterScreenEvent()
    data class ChangeAutoType(val autoType: String) : TripFilterScreenEvent()
    data class ChangeAutoModel(val autoModel: String) : TripFilterScreenEvent()
    data class ChangePriceFrom(val price: Float) : TripFilterScreenEvent()
    data class ChangePriceTo(val price: Float) : TripFilterScreenEvent()
    data class ChangeFromCity(val city: City) : TripFilterScreenEvent()
    data class ChangeToCity(val city: City) : TripFilterScreenEvent()
    data class ChangeTripDate(val date: String) : TripFilterScreenEvent()
    data class ChangeTripTime(val time: String) : TripFilterScreenEvent()
    data class ChangeTripRating(val rating: Int) : TripFilterScreenEvent()

}