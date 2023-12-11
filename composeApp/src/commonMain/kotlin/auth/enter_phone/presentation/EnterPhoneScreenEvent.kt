package auth.enter_phone.presentation

sealed class EnterPhoneScreenEvent {
    data class OnPhoneTextChanged(val phone: String) : EnterPhoneScreenEvent()
    object Continue : EnterPhoneScreenEvent()
    object ResetNavigation : EnterPhoneScreenEvent()
}
