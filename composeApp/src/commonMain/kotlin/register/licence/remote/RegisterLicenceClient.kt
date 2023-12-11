package register.licence.remote

import core.domain.util.Resource

interface RegisterLicenceClient {
    suspend fun register(
        licenceNumber: String,
        expirationDate: String,
    ): Resource<Boolean>
}