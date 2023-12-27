package auth.enter_code.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpResponseDTO(
    @SerialName("result")
    val result: VerifyOtpResultDTO?
)

@Serializable
data class VerifyOtpResultDTO(
    @SerialName("access_token")
    val token: String,
    @SerialName("token_type")
    val tokenType: String?
)