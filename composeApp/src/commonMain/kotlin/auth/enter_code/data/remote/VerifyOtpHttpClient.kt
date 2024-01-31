package auth.enter_code.data.remote

import auth.enter_code.data.remote.mapper.VerifyOtpMapper
import auth.enter_code.domain.VerifyOtpResult
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class VerifyOtpHttpClient(
    private val client: HttpClient,
    private val mapper: VerifyOtpMapper = VerifyOtpMapper()
) : VerifyOtpClient {

    override suspend fun verifyOtp(
        otp: String,
        phone: String,
    ): Resource<VerifyOtpResult> = networkCall(
        map = mapper::mapVerifyResultDto,
        call = {
            client.post {
                url("${AppConstants.BASE_URL}/otp/verify")
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    {
                        "otp": "$otp",
                        "phone": "$phone"
                    }
                    """.trimIndent()
                )
            }
        }
    )
}