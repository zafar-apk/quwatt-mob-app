package tj.ham_safar.app.trips.create.location.presentation

import core.domain.cities.model.City

data class TripLocationScreenState(
    val isPickingCityFrom: Boolean = false,
    val isPickingCityTo: Boolean = false,
    val cities: List<City>? = null,
    val cityFrom: City? = null,
    val cityTo: City? = null,
    val addressFrom: String? = null,
    val addressTo: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val shouldGoNext: Boolean = false,
) {
    companion object {
        const val SECOND_ITEM_INDEX = 1
    }
}