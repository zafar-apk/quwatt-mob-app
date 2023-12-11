package user.domain

class UserInteractor (
    private val repository: UserRepository
) {

    suspend fun setIsUserExist(isUserExist: Boolean) = repository.setIsUserExist(isUserExist)
    suspend fun isUserExist(): Boolean = repository.isUserExist()
    suspend fun isAuthorized(): Boolean = repository.isAuthorized()
    suspend fun logout() = repository.logout()
    suspend fun saveToken(token: String) = repository.saveToken(token)
    suspend fun getToken(): String? = repository.getToken()
    suspend fun getNotificationToken(): String? = repository.getNotificationToken()
    suspend fun saveNotificationToken(token: String) = repository.saveNotificationToken(token)
}