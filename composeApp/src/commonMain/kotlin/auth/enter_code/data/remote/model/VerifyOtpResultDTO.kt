package auth.enter_code.data.remote.model

import auth.enter_code.data.remote.model.UserDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpResultDTO(
    @SerialName("token")
    val token: String,
    @SerialName("user")
    val user: UserDTO?
)