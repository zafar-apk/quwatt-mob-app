package user.data

import com.mmk.kmpnotifier.notification.NotifierManager
import user.data.local.UserLocalDataSource
import user.domain.UserRepository

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun setIsFirstLaunch(isFirst: Boolean) {
        userLocalDataSource.setIsFirstLaunch(isFirst)
    }

    override suspend fun getIsFirstLaunch(): Boolean =
        userLocalDataSource.getIsFirstLaunch()

    override suspend fun setIsUserExist(isUserExist: Boolean) =
        userLocalDataSource.setIsUserExist(isUserExist)

    override suspend fun isUserExist(): Boolean = userLocalDataSource.isUserExist()

    override suspend fun isAuthorized(): Boolean = userLocalDataSource.isAuthorized()

    override suspend fun getToken(): String? = userLocalDataSource.getToken()

    override suspend fun saveToken(token: String) = userLocalDataSource.insertToken(token)

    override suspend fun getRegisterId(): String? = userLocalDataSource.getRegisterId()

    override suspend fun saveRegisterId(id: String) = userLocalDataSource.insertRegisterId(id)

    override suspend fun getNotificationToken(): String? {
        return NotifierManager.getPushNotifier().getToken()
            ?: userLocalDataSource.getNotificationToken()
    }

    override suspend fun saveNotificationToken(token: String) =
        userLocalDataSource.insertNotificationToken(token)

    override suspend fun logout() = userLocalDataSource.logout()
}
