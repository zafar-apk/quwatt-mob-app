package core.domain.util

import android.content.Context
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.PluralStringDesc
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format

actual class Strings(
    private val context: Context
) {
    actual fun get(id: StringResource, args: List<Any>): String {
        return if (args.isEmpty()) {
            StringDesc.Resource(id).toString(context = context)
        } else {
            id.format(*args.toTypedArray()).toString(context)
        }
    }

    actual fun getPlural(id: PluralsResource, count: Int, args: List<Any>): String {
        return if (args.isEmpty()) {
            PluralStringDesc(id, count).toString(context = context)
        } else {
            id.format(count, *args.toTypedArray()).toString(context)
        }
    }
}

actual object DeprecatedStrings {

    lateinit var context: Context

    actual fun get(id: String): String {
        val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
        if (resourceId == 0) return id
        return context.getString(resourceId)
    }

    actual fun get(id: String, quantity: Int): String {
        val resourceId = context.resources.getIdentifier(id, "plurals", context.packageName)
        if (resourceId == 0) return id
        return context.resources.getQuantityString(resourceId, quantity, quantity)
    }

    actual fun format(id: String, vararg formatArgs: Any): String {
        val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
        if (resourceId == 0) return id
        return context.resources.getString(resourceId, *formatArgs)
    }
}