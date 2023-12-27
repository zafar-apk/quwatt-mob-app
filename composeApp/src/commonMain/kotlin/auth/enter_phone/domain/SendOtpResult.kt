package auth.enter_phone.domain

data class SendOtpResult(
    val success: Boolean,
    val result: SendOtpData?
)

data class SendOtpData(
    val code: String?,
    val registerId: String?
)