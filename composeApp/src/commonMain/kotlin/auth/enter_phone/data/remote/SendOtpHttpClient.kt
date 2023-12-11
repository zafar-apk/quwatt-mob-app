package auth.enter_phone.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import auth.enter_phone.data.remote.model.SendOtpRequest
import auth.enter_phone.data.remote.model.SendOtpResultDTO
import auth.enter_phone.domain.SendOtpResult
import core.data.remote.networkCall
import core.domain.util.AppConstants.BASE_URL
import core.domain.util.Resource

class SendOtpHttpClient(
    private val client: HttpClient
) : SendOtpClient {
    override suspend fun sendOtp(phone: String): Resource<SendOtpResult> = networkCall(
        map = ::mapSendOtpResult,
        call = {
            client.post {
                url("$BASE_URL/otp/send")
                contentType(ContentType.Application.Json)
                setBody(SendOtpRequest(phone = phone))
            }
        }
    )

    private fun mapSendOtpResult(dto: SendOtpResultDTO): SendOtpResult = SendOtpResult(
        isSuccess = dto.isSuccess,
        name = dto.name
    )
}