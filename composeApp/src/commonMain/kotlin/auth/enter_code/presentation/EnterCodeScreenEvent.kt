package auth.enter_code.presentation

sealed class EnterCodeScreenEvent {
    object Continue : EnterCodeScreenEvent()
    data class OnCodeChanged(val code: String): EnterCodeScreenEvent()
    data class OnPhoneAvailable(val phone: String): EnterCodeScreenEvent()
    object GoBack : EnterCodeScreenEvent()
    object ForgotPassword : EnterCodeScreenEvent()
    object ResetNavigation : EnterCodeScreenEvent()
}