package core.domain

import core.domain.Strings.setLanguage
import kotlin.concurrent.Volatile
import kotlin.jvm.Synchronized

/**
 * An Object for supporting multiple languages in common code.
 * The language should be changed manually from Android and IOS clients
 * using [setLanguage] function.
 */
object Strings {

    @Volatile
    var language: Language = Language.RU
        private set

    @Synchronized
    fun setLanguage(language: Language) {
        Strings.language = language
    }

    val notRegistered: String
        get() = when (language) {
            Language.RU -> "Вы не зарегистрированы"
            Language.TJ -> "Шумо регистрация накардаед"
        }


    val cannotConnectToTheServerMessage: String
        get() = when (language) {
            Language.RU -> "Не удалось соединиться с сервером, проверьте интернет соединение."
            Language.TJ -> "Барнома ба сервер пайваст карда нашуд, лутфан интернети худро санҷед."
        }

    val unknownError: String
        get() = when (language) {
            Language.RU -> "Ошибка. Попробуйте позже!"
            Language.TJ -> "Хатогӣ рӯй дод, баъдтар бори дигар кӯшиш кунед!"
        }

    val enterValidPhoneNumber: String
        get() = when (language) {
            Language.RU -> "Введите коректный номер телефона"
            Language.TJ -> "Рақами телефони дурустро ворид кунед"
        }

    val enterData: String
        get() = when (language) {
            Language.RU -> "Сначала заполните все поля!"
            Language.TJ -> "Аввал маълумотро ворид кунед!"
        }

    val wrongCode: String
        get() = when (language) {
            Language.RU -> "Неверный код!"
            Language.TJ -> "Рамз нодуруст аст!"
        }

    val invalidDateTimeFormat: String
        get() = when (language) {
            Language.RU -> "Неверный формат даты или времени!"
            Language.TJ -> "Формати сана ё вақт нодуруст!"
        }

}