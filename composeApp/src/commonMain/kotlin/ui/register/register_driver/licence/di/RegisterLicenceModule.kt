package ui.register.register_driver.licence.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import register.licence.domain.RegisterLicence
import register.licence.presentation.RegisterUserLicenceViewModel
import register.licence.remote.RegisterLicenceClient
import register.licence.remote.RegisterLicenceHttpClient

val RegisterLicenceModule = module {

    single<RegisterLicenceClient> {
        RegisterLicenceHttpClient(client = get())
    }

    factoryOf(::RegisterLicence)

    factoryOf(::RegisterUserLicenceViewModel)
}