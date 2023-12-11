package profile.presentation

import profile.domain.User

data class ProfileScreenState(
    val user: User? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val isNotAuthorized: Boolean = false,
    val shouldRegisterUser: Boolean = false,
    val shouldRegisterLicence: Boolean = false,
    val shouldRegisterTransport: Boolean = false,
    val shouldOpenTransport: Boolean = false,
    val shouldOpenLicence: Boolean = false,
    val shouldOpenMyRequests: Boolean = false,
    val shouldOpenMyTrips: Boolean = false,
    val compressedImageNull: Boolean = false
) {
    val shouldReset: Boolean
        get() = shouldRegisterLicence ||
                shouldRegisterTransport ||
                shouldOpenTransport ||
                shouldOpenLicence ||
                shouldRegisterUser ||
                shouldOpenMyRequests ||
                shouldOpenMyTrips ||
                compressedImageNull
}