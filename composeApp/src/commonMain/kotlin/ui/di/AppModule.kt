package ui.di

import io.ktor.client.HttpClient
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import tj.quwatt.quwattapp.core.data.remote.HttpClientFactory
import tj.quwatt.quwattapp.core.data.remote.configureForProject
import user.data.local.UserLocalDataSource

@OptIn(ExperimentalSerializationApi::class)
val NetworkModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }
    }

    single { HttpClientFactory() }

    single<HttpClient> {
        val dataSource: UserLocalDataSource = get()
        val factory: HttpClientFactory = get()
        val json: Json = get()
        factory.create().configureForProject(json) {
            dataSource.getToken().orEmpty()
        }
    }
}