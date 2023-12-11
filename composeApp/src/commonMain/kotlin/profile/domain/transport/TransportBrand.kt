package profile.domain.transport

import kotlinx.serialization.Serializable

@Serializable
enum class TransportBrand {
    OPEL,
    MERCEDES,
    LEXUS,
    TOYOTA,
    LADA,
    DAEWOO,
    BMW,
    AUDI,
    CHEVROLET,
    HYUNDAI,
    FORD,
    JEEP,
    KIA,
    LAND_ROVER,
    MAZDA,
    MITSUBISHI,
    NISSAN,
    PORSCHE,
    RAVON,
    SKODA,
    SSANG_YONG,
    VOLKSWAGEN,
    VOLVO,
    OTHER;

    companion object {

        fun findByDisplayName(typeName: String): TransportBrand = valueOf(typeName.uppercase())
    }

    val displayName: String
        get() = name.lowercase()
            .replaceFirstChar { firstChar -> if (firstChar.isLowerCase()) firstChar.titlecase() else firstChar.toString() }
}