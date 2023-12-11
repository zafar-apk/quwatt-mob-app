package auth.enter_phone.data.remote

import auth.enter_phone.domain.SendOtpResult
import core.domain.util.Resource

interface SendOtpClient {
    suspend fun sendOtp(phone: String): Resource<SendOtpResult>
}