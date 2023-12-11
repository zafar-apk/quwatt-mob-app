package auth.enter_phone.presentation

data class EnterPhoneScreenState(
    val phone: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val next: Boolean = false
)