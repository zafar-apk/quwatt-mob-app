package auth.enter_code.data.remote

import auth.enter_code.domain.VerifyOtpResult
import core.domain.util.Resource

interface VerifyOtpClient {
    suspend fun verifyOtp(
        phone: String,
        otp: String
    ): Resource<VerifyOtpResult>
}