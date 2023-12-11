package auth.enter_code.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class VerifyOtpRequest(
    @SerialName("phone")
    val phone: String,
    @SerialName("otp")
    val otp: String
)
