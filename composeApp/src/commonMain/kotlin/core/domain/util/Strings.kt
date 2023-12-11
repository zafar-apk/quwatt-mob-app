package core.domain.util

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource


expect class Strings {
    fun get(id: StringResource, args: List<Any> = emptyList()): String
    fun getPlural(id: PluralsResource, count: Int, args: List<Any> = emptyList()): String
}

@Composable
@Deprecated("Remove usage asap! It doesn't work. Use Strings instead")
fun stringResource(id: String, args: List<Any> = emptyList(), quantity: Int? = null): String {
    return when {
        quantity != null -> DeprecatedStrings.get(id, quantity)
        args.isNotEmpty() -> DeprecatedStrings.format(id, args)
        else -> DeprecatedStrings.get(id)
    }
}

@Composable
expect fun stringResource(id: StringResource, vararg args: Any): String

@Composable
expect fun stringResource(id: PluralsResource, count: Int, vararg args: Any): String

expect object DeprecatedStrings {
    fun get(id: String, quantity: Int): String
    fun get(id: String): String
    fun format(id: String, vararg formatArgs: Any): String
}