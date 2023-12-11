package edit.user.data

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import edit.user.data.mapper.EditUserMapper
import edit.user.domain.EditUser

class EditUserHttpClient(
    private val client: HttpClient,
    private val mapper: EditUserMapper
) : EditUserClient {

    override suspend fun edit(user: EditUser): Resource<Unit> =
        networkCall(
            map = { _: HttpResponse -> Unit },
            call = {
                client.post {
                    url("${AppConstants.BASE_URL}/edit-user")
                    contentType(ContentType.Application.Json)
                    setBody(mapper.editUserToBody(user))
                }
            }
        )
}