package ui.di

import core.data.remote.HttpClientFactory
import core.data.remote.configureForProject
import io.ktor.client.HttpClient
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
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
            "Bearer ${dataSource.getToken().orEmpty()}"
        }
    }
}