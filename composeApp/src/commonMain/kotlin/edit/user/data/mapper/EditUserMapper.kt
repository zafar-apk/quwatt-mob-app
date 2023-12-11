package edit.user.data.mapper

import edit.transport.data.EditTransportDTO
import edit.transport.domain.models.EditTransport
import edit.user.data.models.EditUserBody
import edit.user.domain.EditUser

class EditUserMapper {

    fun editUserToBody(user: EditUser) = EditUserBody(
        name = user.name,
        surname = user.surname,
        patronymic = user.patronymic,
        dateOfBirth = user.dateOfBirth,
        transport = user.transport?.let { editTransportToBody(it) },
        licenceNumber = user.licenceNumber,
        licenceExpiration = user.licenceExpiration,
        passportNumber = user.passportNumber
    )

    fun editTransportToBody(transport: EditTransport) = EditTransportDTO(
        id = transport.id,
        type = transport.type,
        brand = transport.brand,
        model = transport.model,
        colors = transport.colors,
        capacity = transport.capacity,
        dateOfIssue = transport.dateOfIssue,
        hasConditioner = transport.hasConditioner
    )
}