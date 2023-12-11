package auth.enter_phone.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendOtpRequest(
    @SerialName("phone")
    val phone: String
)