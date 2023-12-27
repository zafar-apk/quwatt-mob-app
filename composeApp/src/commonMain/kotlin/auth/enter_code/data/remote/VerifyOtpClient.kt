package auth.enter_code.data.remote

import auth.enter_code.domain.VerifyOtpResult
import core.domain.util.Resource

interface VerifyOtpClient {
    suspend fun verifyOtp(
        otp: String,
        registerId: String,
        fcmToken: String
    ): Resource<VerifyOtpResult>
}