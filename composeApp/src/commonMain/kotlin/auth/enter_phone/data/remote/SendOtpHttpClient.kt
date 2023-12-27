package auth.enter_phone.data.remote

import auth.enter_phone.data.remote.model.SendOtpResultDTO
import auth.enter_phone.domain.SendOtpData
import auth.enter_phone.domain.SendOtpResult
import core.data.remote.networkCall
import core.domain.util.AppConstants.BASE_URL
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SendOtpHttpClient(
    private val client: HttpClient
) : SendOtpClient {
    override suspend fun sendOtp(phone: String): Resource<SendOtpResult> = networkCall(
        map = ::mapSendOtpResult,
        call = {
            client.post {
                url("$BASE_URL/auth/user-registers?phone_number=$phone")
                contentType(ContentType.Application.Json)
            }
        }
    )

    private fun mapSendOtpResult(dto: SendOtpResultDTO): SendOtpResult = SendOtpResult(
        success = dto.success,
        result = SendOtpData(
            code = dto.result?.code,
            registerId = dto.result?.registerId
        )
    )
}