package auth.enter_code.domain

import profile.domain.User

data class VerifyOtpResult(
    val token: String?,
    val user: User?
)