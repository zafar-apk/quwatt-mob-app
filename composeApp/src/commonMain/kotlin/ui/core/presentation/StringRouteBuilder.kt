package ui.core.presentation


class StringRouteBuilder(root: String) {

    private val stringBuilder = StringBuilder(root)

    fun addArgPlaceHolder(argPlaceHolder: String): StringRouteBuilder {
        stringBuilder
            .append(SLASH)
            .append(OPEN_BRACKET)
            .append(argPlaceHolder)
            .append(CLOSING_BRACKET)
        return this
    }

    fun build() = stringBuilder.toString()

    companion object {
        private const val SLASH = "/"
        private const val OPEN_BRACKET = "{"
        private const val CLOSING_BRACKET = "}"
    }
}

fun String.changePlaceHoldersToArgs(vararg args: Pair<String, String>): String {
    var routeWithArgs: String = this
    args.forEach {
        routeWithArgs = routeWithArgs.replace("{${it.first}}", it.second.toString())
    }
    return routeWithArgs
}
