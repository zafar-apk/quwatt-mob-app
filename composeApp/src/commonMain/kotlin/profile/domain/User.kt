package profile.domain

data class User(
    val id: Int,
    val phone: String,
    val photo: String?,
    val name: String?,
    val surname: String,
    val patronymic: String?,
    val dateOfBirth: String?,
    val email: String?,
    val balance: Double?
) {
    fun getFullName(): String = "$name $surname $patronymic"
}
