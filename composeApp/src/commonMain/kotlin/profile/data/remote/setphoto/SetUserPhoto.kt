package profile.data.remote.setphoto

import core.domain.util.Resource
import profile.domain.User

class SetUserPhoto(
    private val client: SetUserPhotoClient
) {

    suspend fun execute(photo: ByteArray): Resource<Boolean> = client.setUserPhoto(photo)
}