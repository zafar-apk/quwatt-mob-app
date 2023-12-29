package core.domain.util

import kotlinx.datetime.*

/**
 * This Formatter basically works with raw date and time.
 *
 * Example:
 *
 * Raw time - 0800, Formatted time - 08:00
 *
 * Raw date - 08022023, Formatted date - 08/02/2023
 *
 */
fun getCurrentDateTime(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDate.toYYYYmmDD(separator: String = ".") = buildString {
    append(year)
    append(separator)
    append(monthNumber.makeDatePartToHaveTwoSymbols())
    append(separator)
    append(dayOfMonth.makeDatePartToHaveTwoSymbols())
}

private fun Int.makeDatePartToHaveTwoSymbols(): String {
    return takeIf { it > 9 }?.toString() ?: "0$this"
}

class DateTimeFormatter {

    fun validateTime(rawTime: String): Boolean {
        val splitTime = splitRawTime(rawTime)
        if (splitTime.isEmpty()) return false
        val hours = splitTime[0].toIntOrNull() ?: return false
        val minutes = splitTime[1].toIntOrNull() ?: return false

        return hours >= 0 && hours < HOURS_IN_DAY &&
                minutes >= 0 && minutes < MINUTES_IN_HOUR
    }

    fun validateDate(rawTime: String): Boolean {
        val formattedDateStr = formatDate(rawTime) ?: return false
        val regex = """^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/\d{4}$""".toRegex()
        return if (regex.matches(formattedDateStr)) {
            val (day, month, year) = formattedDateStr.split(SLASH)
            runCatching {
                LocalDate(year.toInt(), month.toInt(), day.toInt())
                true
            }.getOrDefault(false)
        } else {
            false
        }
    }

    fun isDateTimeInvalid(rawTime: String?, rawDate: String?): Boolean {
        rawTime ?: return true
        rawDate ?: return true

        val splitRawDate = splitRawDate(rawDate)
        val splitRawTime = splitRawTime(rawTime)

        if (splitRawDate.isEmpty()) return true
        if (splitRawTime.isEmpty()) return true

        val (day, month, year) = splitRawDate
        val (hour, minute) = splitRawTime

        val areDataInValid = validateDate(rawDate).not() || validateTime(rawTime).not()
        if (areDataInValid) return areDataInValid

        val areDateOrTimeElapsed = runCatching {
            val dateTime = LocalDateTime(
                year.toInt(), month.toInt(), day.toInt(), hour.toInt(), minute.toInt(), 0, 0
            )
            val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
            val chosenTimeInMillis =
                dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            currentTimeMillis > chosenTimeInMillis
        }.getOrDefault(true)
        return areDateOrTimeElapsed
    }

    private fun splitRawTime(rawTime: String): List<String> {
        if (rawTime.length < RAW_TIME_LENGTH) return emptyList()
        val hour = rawTime.substring(0, 2)
        val minute = rawTime.substring(2, 4)
        return listOf(hour, minute)
    }

    fun formatTime(rawTime: String?): String? {
        rawTime ?: return null
        val splitTime = splitRawTime(rawTime)
        if (splitTime.isEmpty()) return null
        return splitTime.joinToString(COLON)
    }

    private fun splitRawDate(rawDate: String): List<String> {
        if (rawDate.length < RAW_DATE_LENGTH) return emptyList()
        val day = rawDate.substring(0, 2)
        val month = rawDate.substring(2, 4)
        val year = rawDate.substring(4, 8)
        return listOf(day, month, year)
    }

    fun formatDate(rawDate: String?): String? {
        rawDate ?: return null
        val splitDate = splitRawDate(rawDate)
        if (splitDate.isEmpty()) return null
        return splitDate.joinToString(SLASH)
    }

    fun toRawDate(formattedDate: String): String =
        formattedDate.replace(SLASH, EMPTY)

    fun toRawTime(formattedTime: String): String =
        formattedTime.replace(COLON, EMPTY)

    companion object {
        private const val COLON = ":"
        private const val SLASH = "/"
        private const val EMPTY = ""
        private const val HOURS_IN_DAY = 24
        private const val MINUTES_IN_HOUR = 60
        private const val RAW_TIME_LENGTH = 4
        private const val RAW_DATE_LENGTH = 8

    }
}