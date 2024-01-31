package auth.enter_code.domain

import auth.enter_code.data.remote.VerifyOtpClient
import core.domain.util.Resource

class VerifyOtp(
    private val verifyOtpClient: VerifyOtpClient
) {
    suspend fun execute(
        otp: String,
        phone: String
    ): Resource<VerifyOtpResult> {
        return verifyOtpClient.verifyOtp(
            otp = otp,
            phone = "992$phone"
        )
    }

}