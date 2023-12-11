package user.data

import user.data.local.UserLocalDataSource
import user.domain.UserRepository

class UserRepositoryImpl(
    private val dataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun setIsFirstLaunch(isFirst: Boolean) {
        dataSource.setIsFirstLaunch(isFirst)
    }

    override suspend fun getIsFirstLaunch(): Boolean =
        dataSource.getIsFirstLaunch()

    override suspend fun setIsUserExist(isUserExist: Boolean) =
        dataSource.setIsUserExist(isUserExist)

    override suspend fun isUserExist(): Boolean = dataSource.isUserExist()

    override suspend fun isAuthorized(): Boolean = dataSource.isAuthorized()

    override suspend fun getToken(): String? = dataSource.getToken()

    override suspend fun saveToken(token: String) = dataSource.insertToken(token)

    override suspend fun getNotificationToken(): String? = dataSource.getNotificationToken()

    override suspend fun saveNotificationToken(token: String) =
        dataSource.insertNotificationToken(token)

    override suspend fun logout() = dataSource.logout()
}
