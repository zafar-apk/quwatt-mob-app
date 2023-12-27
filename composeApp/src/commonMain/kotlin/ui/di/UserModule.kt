package ui.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import profile.data.remote.getuser.GetUser
import profile.data.remote.getuser.GetUserClient
import profile.data.remote.getuser.GetUserHttpClient
import user.data.UserRepositoryImpl
import user.data.local.UserLocalDataSource
import user.domain.UserInteractor
import user.domain.UserRepository

val UserModule = module {

    single<GetUserClient> {
        GetUserHttpClient(client = get())
    }

    factoryOf(::UserLocalDataSource)

    factory<UserRepository> { UserRepositoryImpl(get()) }

    factoryOf(::UserInteractor)

    factoryOf(::GetUser)
}