package auth.enter_phone.data.remote

import auth.enter_phone.data.remote.model.SendOtpResultDTO
import core.data.remote.networkCall
import core.domain.util.AppConstants.BASE_URL
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SendOtpHttpClient(
    private val client: HttpClient
) : SendOtpClient {
    override suspend fun sendOtp(phone: String): Resource<Boolean> = networkCall(
        map = ::mapSendOtpResult,
        call = {
            client.post {
                url("$BASE_URL/otp/send")
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    {
                        "phone": "$phone"
                    }
                    """.trimIndent()
                )
            }
        }
    )

    private fun mapSendOtpResult(dto: SendOtpResultDTO): Boolean = dto.isSuccess
}