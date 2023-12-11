package edit.user.domain

import edit.transport.domain.models.EditTransport

class EditUser(
    val name: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val dateOfBirth: String? = null,
    val transport: EditTransport? = null,
    val licenceNumber: String? = null,
    val licenceExpiration: String? = null,
    val passportNumber: String? = null
)