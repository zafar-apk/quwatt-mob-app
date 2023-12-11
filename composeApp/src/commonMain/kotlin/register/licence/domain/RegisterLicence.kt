package register.licence.domain

import core.domain.util.Resource
import register.licence.remote.RegisterLicenceClient

class RegisterLicence(private val registerLicenceClient: RegisterLicenceClient) {
    suspend fun execute(
        licenceNumber: String,
        licenceExpiration: String
    ): Resource<Boolean> = registerLicenceClient.register(
        licenceNumber = licenceNumber, expirationDate = licenceExpiration
    )
}