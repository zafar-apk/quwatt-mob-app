package auth.enter_code.domain

data class VerifyOtpResult(
    val token: String?,
    val tokenType: String?
)