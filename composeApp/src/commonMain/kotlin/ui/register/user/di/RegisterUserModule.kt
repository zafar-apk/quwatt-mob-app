package ui.register.user.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import register.user.data.remote.RegisterUserClient
import register.user.data.remote.RegisterUserHttpClient
import register.user.domain.RegisterUser
import register.user.presentation.RegisterUserViewModel


val RegisterUserModule = module {

    factory<RegisterUserClient> { RegisterUserHttpClient(get()) }

    factoryOf(::RegisterUser)

    factoryOf(::RegisterUserViewModel)
}