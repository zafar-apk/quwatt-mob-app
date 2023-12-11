package profile.domain.transport

import kotlinx.serialization.Serializable


@Serializable
enum class TransportColors(val displayName: String) {
    WHITE("Белый"),
    BLACK("Чёрный"),
    GRAY("Серый"),
    SILVER("Серебро"),
    BLUE("Синий"),
    RED("Красный"),
    BROWN("Коричневый"),
    GREEN("Зелёный"),
    ORANGE("Оранжевый"),
    YELLOW("Жёлтый"),
    OTHER("Другой");

    companion object {
        fun findByDisplayName(name: String): TransportColors? = values().firstOrNull {
            it.displayName == name
        }
    }
}