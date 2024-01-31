package user.domain

@Deprecated("Use repo directly. Interactor is meant for merging several use cases not repos")
class UserInteractor (
    private val repository: UserRepository
) {

    suspend fun setIsUserExist(isUserExist: Boolean) = repository.setIsUserExist(isUserExist)
    suspend fun isUserExist(): Boolean = repository.isUserExist()
    suspend fun isAuthorized(): Boolean = repository.isAuthorized()
    suspend fun logout() = repository.logout()
    suspend fun saveToken(token: String) = repository.saveToken(token)
    suspend fun getNotificationToken(): String? = repository.getNotificationToken()
    suspend fun saveNotificationToken(token: String) = repository.saveNotificationToken(token)
}