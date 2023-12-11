package core.domain.util

import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.stringWithFormat

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.PluralStringDesc
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.desc.Plural
import dev.icerock.moko.resources.format

actual class Strings {
    actual fun get(id: StringResource, args: List<Any>): String {
        return if (args.isEmpty()) {
            StringDesc.Resource(id).localized()
        } else {
            id.format(*args.toTypedArray()).localized()
        }
    }

    actual fun getPlural(id: PluralsResource, count: Int, args: List<Any>): String {
        return if (args.isEmpty()) {
            StringDesc.Plural(id, count).localized()
        } else {
            id.format(count, *args.toTypedArray()).localized()
        }
    }
}

actual object DeprecatedStrings {
    actual fun get(id: String): String = id.localized()
    actual fun get(id: String, quantity: Int) = id.localized(quantity)
    actual fun format(id: String, vararg formatArgs: Any) = id.localized(*formatArgs)
}

fun String.localized(): String {
    val localizedString = NSBundle.mainBundle.localizedStringForKey(this, this, null)
    if (localizedString != this) return localizedString

    val baseResourcePath = NSBundle.mainBundle.pathForResource("Base", "lproj")
        ?.let { NSURL(fileURLWithPath = it) }
    val baseBundle = baseResourcePath?.let { NSBundle(it) }

    if (baseBundle != null) {
        val baseString = baseBundle.localizedStringForKey(this, this, null)
        if (baseString != this) return baseString
    }

    return this
}

fun String.localized(vararg arguments: Any?): String {
    val format = localized()
    // Shorten the variable name
    val a = arguments
    // Kotlin does not support passing variadic parameters to Objective-C
    // We implement calling the method with up to 9 arguments which is enough in practice
    return when (arguments.size) {
        0 -> NSString.stringWithFormat(format)
        1 -> NSString.stringWithFormat(format, a[0])
        2 -> NSString.stringWithFormat(format, a[0], a[1])
        3 -> NSString.stringWithFormat(format, a[0], a[1], a[2])
        4 -> NSString.stringWithFormat(format, a[0], a[1], a[2], a[3])
        5 -> NSString.stringWithFormat(format, a[0], a[1], a[2], a[3], a[4])
        6 -> NSString.stringWithFormat(format, a[0], a[1], a[2], a[3], a[4], a[5])
        7 -> NSString.stringWithFormat(format, a[0], a[1], a[2], a[3], a[4], a[5], a[6])
        8 -> NSString.stringWithFormat(format, a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7])
        9 -> NSString.stringWithFormat(format, a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8])
        else -> error("Too many arguments.")
    }
}