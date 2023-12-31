package ui.register.register_driver.transport.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import register.transport.domain.RegisterTransport
import register.transport.remote.RegisterTransportClient
import register.transport.remote.RegisterTransportHttpClient


val RegisterTransportModule = module {

    factory<RegisterTransportClient> { RegisterTransportHttpClient(get()) }

    factoryOf(::RegisterTransport)
}