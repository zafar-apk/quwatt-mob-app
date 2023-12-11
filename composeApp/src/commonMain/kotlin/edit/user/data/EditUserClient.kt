package edit.user.data

import core.domain.util.Resource
import edit.user.domain.EditUser

interface EditUserClient {
    suspend fun edit(user: EditUser): Resource<Unit>
}