package edit.licence.domain

import core.domain.util.Resource
import edit.user.data.EditUserClient
import edit.user.domain.EditUser

class EditLicenceUseCase(
    private val client: EditUserClient
) {
    suspend operator fun invoke(
        licenceNumber: String,
        licenceExpiration: String
    ): Resource<Unit> =
        client.edit(
            EditUser(
                licenceNumber = licenceNumber,
                licenceExpiration = licenceExpiration
            )
        )

}