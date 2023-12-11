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

    private fun mapUser(dto: UserDTO): User = dto.toUser()

    override suspend fun setUserPhoto(photo: ImageFile): Resource<User> = runCatching {
        val response = client.submitFormWithBinaryData(
            url = "${AppConstants.BASE_URL}/user/set-photo",
            formData = createFormData(photo = photo)
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }
        val userDto = response.body<UserDTO>()
        Resource.Success(mapUser(userDto))
    }.getOrElse {
        it.printStackTrace()
        Resource.Error(it)
    }

    private fun createFormData(photo: ImageFile?): List<PartData> = formData {
        photo?.let { imageFile ->
            appendPhoto(imageFile)
        }
    }

    private fun FormBuilder.appendPhoto(imageFile: ImageFile) {
        append(
            key = "photo",
            value = imageFile.toByteArray(),
            headers = Headers.build {
                append(HttpHeaders.ContentType, "image/jpeg")
                append(HttpHeaders.ContentDisposition, "filename=image.png")
            }
        )
    }
}