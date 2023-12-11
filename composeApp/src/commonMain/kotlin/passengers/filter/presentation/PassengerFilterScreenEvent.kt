package passengers.filter.presentation

import core.domain.cities.model.City
import passengers.all.domain.PassengerFilter

sealed class PassengerFilterScreenEvent {

    object Submit : PassengerFilterScreenEvent()
    object ResetFilter : PassengerFilterScreenEvent()
    object BackPress : PassengerFilterScreenEvent()
    data class GoBackWithResult<T>(val filterResult: T) : PassengerFilterScreenEvent()
    object OpenToCityDropDown : PassengerFilterScreenEvent()
    object OpenFromCityDropDown : PassengerFilterScreenEvent()
    object OpenTripDateCalendar : PassengerFilterScreenEvent()
    object OpenTripTimeDialog : PassengerFilterScreenEvent()
    object OpenTripRatingDropDown : PassengerFilterScreenEvent()
    object DismissAllChoosing : PassengerFilterScreenEvent()
    object OnPriceValuesReseted : PassengerFilterScreenEvent()
    data class ChangePriceFrom(val price: Int) : PassengerFilterScreenEvent()
    data class ChangePriceTo(val price: Int) : PassengerFilterScreenEvent()
    data class ChangeFromCity(val city: City) : PassengerFilterScreenEvent()
    data class ChangeToCity(val city: City) : PassengerFilterScreenEvent()
    data class ChangeTripDate(val date: String) : PassengerFilterScreenEvent()
    data class ChangeTripTime(val time: String) : PassengerFilterScreenEvent()
    data class ChangeTripRating(val rating: Int) : PassengerFilterScreenEvent()
    data class PassengersFilterDataReturned(val filter: PassengerFilter) : PassengerFilterScreenEvent()
}