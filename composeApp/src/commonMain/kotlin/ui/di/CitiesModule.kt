package ui.di

import core.data.remote.cities.CitiesClient
import core.data.remote.cities.CitiesHttpClient
import core.domain.cities.use_case.GetCities
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val CitiesModule = module {

    factory<CitiesClient> { CitiesHttpClient(get()) }

    factoryOf(::GetCities)
}