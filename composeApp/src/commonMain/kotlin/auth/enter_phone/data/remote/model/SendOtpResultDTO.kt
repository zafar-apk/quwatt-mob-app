package auth.enter_phone.data.remote.model

import auth.enter_phone.domain.SendOtpData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResultDTO(
    @SerialName("success")
    val success: Boolean,
    @SerialName("result")
    val result: SendOtpDataDTO?
)

@Serializable
data class SendOtpDataDTO(
    @SerialName("sms_code")
    val code: String?,
    @SerialName("user_register_id")
    val registerId: String?
)