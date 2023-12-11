package auth.enter_code.presentation

data class EnterCodeScreenState(
    val name: String = "",
    val phone: String = "",
    val code: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val navigation: EnterCodeNavigation? = null
)