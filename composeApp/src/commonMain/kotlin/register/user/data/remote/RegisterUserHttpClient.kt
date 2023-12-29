package register.user.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import core.domain.util.AppConstants
import core.domain.util.ImageFile
import core.domain.util.Resource
import core.domain.util.toByteArray

class RegisterUserHttpClient(
    private val client: HttpClient
) : RegisterUserClient {

    override suspend fun register(
        name: String,
        surname: String,
        patronymic: String,
        dateOfBirth: String,
        photo: ByteArray?
    ): Resource<Boolean> = runCatching {
        client.submitFormWithBinaryData(
            url = "${AppConstants.BASE_URL}/register", // todo add new api integration
            formData = createFormData(
                name = name,
                surname = surname,
                patronymic = patronymic,
                dateOfBirth = dateOfBirth,
                photo = photo
            )
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }
        Resource.Success(true)
    }.getOrElse {
        it.printStackTrace()
        Resource.Error(it)
    }

    private fun createFormData(
        name: String,
        surname: String,
        patronymic: String,
        dateOfBirth: String,
        photo: ByteArray?
    ): List<PartData> = formData {
        append("name", name)
        append("surname", surname)
        append("patronymic", patronymic)
        append("dateOfBirth", dateOfBirth)
        photo?.let { imageFile ->
            appendPhoto(imageFile)
        }
    }

    private fun FormBuilder.appendPhoto(imageFile: ByteArray) {
        append(
            key = "photo",
            value = imageFile,
            headers = Headers.build {
                append(HttpHeaders.ContentType, "image/jpeg")
                append(HttpHeaders.ContentDisposition, "filename=image.jpeg")
            }
        )
    }
}