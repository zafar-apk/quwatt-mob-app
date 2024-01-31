package auth.enter_code.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpResponseDTO(
    @SerialName("token")
    val token: String,
    @SerialName("user")
    val user: UserDTO? = null
)