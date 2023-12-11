package profile.data.remote.setphoto

import core.domain.util.ImageFile
import core.domain.util.Resource
import profile.domain.User

class SetUserPhoto(
    private val client: SetUserPhotoClient
) {

    suspend fun execute(photo: ImageFile): Resource<User> = client.setUserPhoto(photo)
}