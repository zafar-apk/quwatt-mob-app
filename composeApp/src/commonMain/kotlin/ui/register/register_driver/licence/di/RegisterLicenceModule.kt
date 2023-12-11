package ui.register.register_driver.licence.di

import io.ktor.client.HttpClient
import register.licence.domain.RegisterLicence
import register.licence.remote.RegisterLicenceClient
import register.licence.remote.RegisterLicenceHttpClient

//@Module
//@InstallIn(ViewModelComponent::class)
//object RegisterLicenceModule {
//
//    @Provides
//    @ViewModelScoped
//    fun provideRegisterLicenceClient(httpClient: HttpClient): RegisterLicenceClient =
//        RegisterLicenceHttpClient(client = httpClient)
//
//    @Provides
//    @ViewModelScoped
//    fun provideRegisterUser(client: RegisterLicenceClient): RegisterLicence =
//        RegisterLicence(registerLicenceClient = client)
//}