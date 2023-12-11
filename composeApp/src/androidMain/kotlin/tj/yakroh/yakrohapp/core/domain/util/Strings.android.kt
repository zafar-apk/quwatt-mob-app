package core.domain.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource

@Composable
actual fun stringResource(
    id: StringResource,
    vararg args: Any
): String {
    return Strings(LocalContext.current).get(id, args.toList())
}

@Composable
actual fun stringResource(
    id: PluralsResource,
    count: Int,
    vararg args: Any
): String {
    return Strings(LocalContext.current).getPlural(id, count, args.toList())
}