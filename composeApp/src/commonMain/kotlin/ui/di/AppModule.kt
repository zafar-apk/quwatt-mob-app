package ui.di

import core.data.remote.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import user.data.local.UserLocalDataSource
import user.domain.UserInteractor

val NetworkModule = module {

    single { HttpClientFactory() }

    single<HttpClient> {
        val dataSource: UserLocalDataSource = get()
        val factory: HttpClientFactory = get()
        factory.create {
            dataSource.getToken().orEmpty()
        }
    }
}