package user.domain

interface UserRepository {

    suspend fun setIsFirstLaunch(isFirst: Boolean)

    suspend fun getIsFirstLaunch(): Boolean

    suspend fun setIsUserExist(isUserExist: Boolean)

    suspend fun isUserExist(): Boolean

    suspend fun isAuthorized(): Boolean

    suspend fun logout()

    suspend fun getToken(): String?

    suspend fun getNotificationToken(): String?

    suspend fun saveNotificationToken(token: String)

    suspend fun saveToken(token: String)

    suspend fun getRegisterId(): String?

    suspend fun saveRegisterId(id: String)
}