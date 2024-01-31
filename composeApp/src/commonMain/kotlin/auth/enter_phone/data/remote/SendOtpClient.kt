package auth.enter_phone.data.remote

import core.domain.util.Resource

interface SendOtpClient {
    suspend fun sendOtp(phone: String): Resource<Boolean>
}