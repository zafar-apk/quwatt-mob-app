package auth.enter_phone.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResultDTO(
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("name")
    val name: String?
)