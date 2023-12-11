package ui.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import user.data.UserRepositoryImpl
import user.data.local.UserLocalDataSource
import user.domain.UserInteractor
import user.domain.UserRepository

val UserModule = module {

    factoryOf(::UserLocalDataSource)

    factory<UserRepository> { UserRepositoryImpl(get()) }

    factoryOf(::UserInteractor)
}