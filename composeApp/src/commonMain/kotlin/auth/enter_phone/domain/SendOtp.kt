package auth.enter_phone.domain

import auth.enter_phone.data.remote.SendOtpClient
import core.domain.util.Resource

class SendOtp(
    private val sendOtpClient: SendOtpClient
) {
    suspend fun execute(
        phone: String
    ): Resource<SendOtpResult> = sendOtpClient.sendOtp(phone = "992$phone")
}