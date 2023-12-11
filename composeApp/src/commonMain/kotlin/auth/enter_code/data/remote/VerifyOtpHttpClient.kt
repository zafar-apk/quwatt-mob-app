package auth.enter_code.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import auth.enter_code.data.remote.mapper.VerifyOtpMapper.mapVerifyResultDto
import auth.enter_code.data.remote.model.VerifyOtpRequest
import auth.enter_code.domain.VerifyOtpResult
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource

class VerifyOtpHttpClient(
    private val client: HttpClient
) : VerifyOtpClient {
    override suspend fun verifyOtp(
        phone: String,
        otp: String
    ): Resource<VerifyOtpResult> = networkCall(
        map = ::mapVerifyResultDto,
        call = {
            client.post {
                url("${AppConstants.BASE_URL}/otp/verify")
                contentType(ContentType.Application.Json)
                setBody(VerifyOtpRequest(phone = phone, otp = otp))
            }
        }
    )
}