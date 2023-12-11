package auth.enter_code.domain

import auth.enter_code.data.remote.VerifyOtpClient
import core.domain.util.Resource

class VerifyOtp(
    private val verifyOtpClient: VerifyOtpClient
) {
    suspend fun execute(
        phone: String,
        otp: String
    ): Resource<VerifyOtpResult> = verifyOtpClient.verifyOtp(
        phone = "992$phone",
        otp = otp
    )
}