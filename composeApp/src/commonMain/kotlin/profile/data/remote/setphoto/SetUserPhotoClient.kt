package profile.data.remote.setphoto

import core.domain.util.Resource
import profile.domain.User

interface SetUserPhotoClient {
    suspend fun setUserPhoto(photo: ByteArray): Resource<Boolean>
}