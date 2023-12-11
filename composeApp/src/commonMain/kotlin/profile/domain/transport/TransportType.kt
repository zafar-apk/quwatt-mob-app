package profile.domain.transport

import kotlinx.serialization.Serializable

@Serializable
enum class TransportType(val displayName: String) {
    SEDAN("Седан"),
    OFFROAD("Внедорожник"),
    HATCHBACK("Хэтчбек"),
    UNIVERSAL("Универсал(Караван)"),
    MICROBUS("Микроавтобус"),
    MINIVAN("Минивэн");

    companion object {
        fun findByDisplayName(name: String): TransportType? =
            values().find { it.displayName == name }
    }
}