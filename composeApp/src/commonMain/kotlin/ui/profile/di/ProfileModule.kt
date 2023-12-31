package ui.profile.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import profile.data.remote.getuser.GetUser
import profile.data.remote.getuser.GetUserClient
import profile.data.remote.getuser.GetUserHttpClient
import profile.data.remote.setphoto.SetUserPhoto
import profile.data.remote.setphoto.SetUserPhotoClient
import profile.data.remote.setphoto.SetUserPhotoHttpClient
import profile.presentation.ProfileScreenViewModel

val ProfileModule = module {

    factoryOf(::GetUser)

    factory<GetUserClient> { GetUserHttpClient(get()) }

    factoryOf(::SetUserPhoto)

    factory<SetUserPhotoClient> { SetUserPhotoHttpClient(get()) }

    factoryOf(::ProfileScreenViewModel)
}