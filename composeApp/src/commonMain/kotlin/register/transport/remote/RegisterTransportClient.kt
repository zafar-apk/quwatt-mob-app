package register.transport.remote

import core.domain.util.ImageFile
import core.domain.util.Resource
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType

interface RegisterTransportClient {
    suspend fun register(
        type: TransportType,
        brand: TransportBrand,
        model: String,
        color: TransportColors,
        dateOfIssue: String,
        capacity: Int,
        hasConditioner: Boolean,
        photo: ImageFile?
    ): Resource<Unit>
}