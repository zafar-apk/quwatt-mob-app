package profile.data.remote.setphoto

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.content.*
import auth.enter_code.data.remote.model.UserDTO
import auth.enter_code.data.remote.model.toUser
import core.domain.util.AppConstants
import core.domain.util.ImageFile
import core.domain.util.Resource
import core.domain.util.toByteArray
import profile.domain.User

class SetUserPhotoHttpClient(
    private val client: HttpClient
) : SetUserPhotoClient {

    override suspend fun setUserPhoto(photo: ByteArray): Resource<Boolean> = runCatching {
        val response = client.submitFormWithBinaryData(
            url = "${AppConstants.BASE_URL}/users/avatar/upload",
            formData = createFormData(photo = photo)
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }
        Resource.Success(response.status.isSuccess())
    }.getOrElse {
        it.printStackTrace()
        Resource.Error(it)
    }

    private fun createFormData(photo: ByteArray): List<PartData> = formData {
            appendPhoto(photo)
    }

    private fun FormBuilder.appendPhoto(imageFile: ByteArray) {
        append(
            key = "avatar",
            value = imageFile,
            headers = Headers.build {
                append(HttpHeaders.ContentType, "image/jpeg")
                append(HttpHeaders.ContentDisposition, "filename=image.jpeg")
            }
        )
    }
}