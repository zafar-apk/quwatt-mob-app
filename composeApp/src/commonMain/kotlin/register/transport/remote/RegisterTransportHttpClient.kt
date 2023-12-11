package register.transport.remote

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import core.data.remote.RemoteError
import core.domain.util.AppConstants
import core.domain.util.ImageFile
import core.domain.util.Resource
import core.domain.util.toByteArray
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType

class RegisterTransportHttpClient(
    private val client: HttpClient
) : RegisterTransportClient {

    override suspend fun register(
        type: TransportType,
        brand: TransportBrand,
        model: String,
        color: TransportColors,
        dateOfIssue: String,
        capacity: Int,
        hasConditioner: Boolean,
        photo: ImageFile?
    ): Resource<Unit> = runCatching {
        val result = client.submitFormWithBinaryData(
            url = "${AppConstants.BASE_URL}/register/transport",
            formData = createFormData(
                type = type,
                brand = brand,
                model = model,
                color = color,
                dateOfIssue = dateOfIssue,
                capacity = capacity,
                hasConditioner = hasConditioner,
                photo = photo
            )
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }
        if (result.status.isSuccess()) {
            Resource.Success(Unit)
        } else {
            Resource.Error(RemoteError(result.bodyAsText(), result.status.value))
        }
    }.getOrElse {
        it.printStackTrace()
        Resource.Error(it)
    }

    private fun createFormData(
        type: TransportType,
        brand: TransportBrand,
        model: String,
        color: TransportColors,
        dateOfIssue: String,
        capacity: Int,
        hasConditioner: Boolean,
        photo: ImageFile?
    ): List<PartData> = formData {
        append("type", type.name)
        append("brand", brand.name)
        append("model", model)
        append("color", color.name)
        append("dateOfIssue", dateOfIssue)
        append("capacity", capacity)
        append("hasConditioner", hasConditioner.toString())
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