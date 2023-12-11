package core.data.remote

class RemoteError(
    override val message: String?,
    val responseCode: Int
): Exception()