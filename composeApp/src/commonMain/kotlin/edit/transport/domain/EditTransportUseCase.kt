package edit.transport.domain

import core.domain.util.Resource
import edit.transport.domain.models.EditTransport
import edit.user.data.EditUserClient
import edit.user.domain.EditUser

class EditTransportUseCase(
    private val client: EditUserClient
) {
    suspend operator fun invoke(transport: EditTransport): Resource<Unit> =
        client.edit(EditUser(transport = transport))

}