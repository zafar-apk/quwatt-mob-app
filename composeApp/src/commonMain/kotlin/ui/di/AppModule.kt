package ui.di

import core.data.remote.HttpClientFactory
import core.data.remote.configureForProject
import io.ktor.client.HttpClient
import org.koin.dsl.module
import user.data.local.UserLocalDataSource

val NetworkModule = module {

    single { HttpClientFactory() }

    single<HttpClient> {
        val dataSource: UserLocalDataSource = get()
        val factory: HttpClientFactory = get()
        factory.create().configureForProject {
            "Bearer ${dataSource.getToken().orEmpty()}"
        }
    }
}