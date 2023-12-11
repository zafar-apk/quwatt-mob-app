package register.transport.domain

import core.domain.util.ImageFile
import core.domain.util.Resource
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType
import register.transport.remote.RegisterTransportClient

class RegisterTransport(private val registerTransportClient: RegisterTransportClient) {
    suspend fun execute(
        type: TransportType,
        brand: TransportBrand,
        model: String,
        colors: TransportColors,
        dateOfIssue: String,
        capacity: Int,
        hasAC: Boolean,
        photo: ImageFile?
    ): Resource<Unit> = registerTransportClient.register(
        type = type,
        brand = brand,
        model = model,
        color = colors,
        dateOfIssue = dateOfIssue,
        capacity = capacity,
        hasConditioner = hasAC,
        photo = photo
    )
}