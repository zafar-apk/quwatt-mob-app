package auth.enter_code.domain

import auth.enter_code.data.remote.VerifyOtpClient
import com.mmk.kmpnotifier.notification.NotifierManager
import core.domain.util.Resource
import user.domain.UserRepository

class VerifyOtp(
    private val verifyOtpClient: VerifyOtpClient,
    private val userRepository: UserRepository
) {
    suspend fun execute(otp: String): Resource<VerifyOtpResult> {
        val registerId = userRepository.getRegisterId().orEmpty()
        val fcmToken = userRepository.getNotificationToken().orEmpty()
        return verifyOtpClient.verifyOtp(
            otp = otp,
            registerId = registerId,
            fcmToken = fcmToken
        )
    }
}