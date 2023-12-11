package ui.core.utils

import io.ktor.http.HttpHeaders.Date

// TODO replace with kmp solutions
fun Long.toDateDDMMYYYY(): String {
    return toString()
//    val date = Date(this)
//    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//    return format.format(date)
}

// TODO replace with kmp solutions
fun Long.toTimeHHmm(): String {
    return toString()
//    val localTime = LocalTime.ofNanoOfDay(this)
//    val formatter = DateTimeFormatter.ofPattern("HH:mm")
//    return localTime.format(formatter)
}