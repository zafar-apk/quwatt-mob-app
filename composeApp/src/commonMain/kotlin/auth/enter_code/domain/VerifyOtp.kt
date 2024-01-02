package auth.enter_code.domain

import auth.enter_code.data.remote.VerifyOtpClient
import core.data.remote.ApiErrorResponse
import core.data.remote.RemoteError
import core.domain.Strings
import core.domain.util.Resource
import kotlinx.serialization.json.Json
import user.domain.UserRepository

class VerifyOtp(
    private val verifyOtpClient: VerifyOtpClient,
    private val userRepository: UserRepository,
    private val json: Json
) {
    suspend fun execute(otp: String): Resource<VerifyOtpResult> {
        val registerId = userRepository.getRegisterId().orEmpty()
        val fcmToken = userRepository.getNotificationToken().orEmpty()
        val result = verifyOtpClient.verifyOtp(
            otp = otp,
            registerId = registerId,
            fcmToken = fcmToken
        )

        if (result is Resource.Error) {
            val throwable = result.throwable
            val responseJson = throwable?.message ?: return result
            val errorResponse = json.decodeFromString<ApiErrorResponse>(responseJson)
            if (errorResponse.message?.contains(MESSAGE_WRONG_CODE) == true) {
                val wrongErrorCodeError = RemoteError(
                    message = Strings.wrongCode,
                    responseCode = errorResponse.code ?: 0
                )
                return Resource.Error(throwable = wrongErrorCodeError)
            }
        }
        return result
    }

    private companion object {
        const val MESSAGE_WRONG_CODE = "message.sms_code_not_found"
    }
}