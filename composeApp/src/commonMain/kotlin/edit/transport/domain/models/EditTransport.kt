package edit.transport.domain.models

import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType

class EditTransport(
    val id: Int,
    val type: TransportType,
    val brand: TransportBrand,
    val model:String,
    val colors: TransportColors,
    val capacity: Int,
    val dateOfIssue: String,
    val hasConditioner: Boolean
)
