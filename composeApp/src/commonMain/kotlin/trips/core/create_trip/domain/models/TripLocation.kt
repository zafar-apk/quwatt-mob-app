package tj.ham_safar.app.trips.core.create_trip.domain.models

import core.domain.cities.model.City

data class TripLocation(
    val cityFrom: City,
    val cityTo: City,
    val addressFrom: String,
    val addressTo: String,
)
