package tj.ham_safar.app.android.core.presentation

import ui.root.Arguments
import ui.root.Arguments.CompleteAction

@Deprecated("Create a file which contains composable and route within instead")
object Routes {

    fun createTripLocation(completeNavigation: Int): String = CREATE_TRIP_LOCATION.replace(
        "{$CompleteAction}",
        completeNavigation.toString()
    )

    fun createTripDateTime(completeNavigation: Int): String = CREATE_TRIP_DATE_TIME.replace(
        "{$CompleteAction}",
        completeNavigation.toString()
    )

    const val DETAILED_PASSENGER = "passenger/{${Arguments.passengerId}}"
    const val ON_BOARDING = "on-boarding"

    const val ENTER_PHONE = "enter-phone"

    const val ENTER_CODE = "enter-code"
    const val REGISTER_USER = "register-user"

    const val FORGOT_PASSWORD = "forgot-password"

    const val TRIPS_HISTORY = "trips-history"

    const val CREATE_TRIP_LOCATION = "trip-create-location/{${Arguments.CompleteAction}}"
    const val CREATE_TRIP_DATE_TIME = "trip-create-date-time/{${Arguments.CompleteAction}}"

    const val MESSAGES = "messages"

    const val PROFILE = "profile"
    const val DETAILED_TRIP = "detailed_trip/{${Arguments.TripId}}"
    const val CHOOSE_SPOT = "choose_spot/{${Arguments.TripId}}"
}