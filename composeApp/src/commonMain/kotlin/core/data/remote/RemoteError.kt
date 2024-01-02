package core.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RemoteError(
    override val message: String?,
    val responseCode: Int
): Exception()

@Serializable
data class ApiErrorResponse(
    @SerialName("message")
    val message: String?,
    @SerialName("code")
    val code: Int?
)