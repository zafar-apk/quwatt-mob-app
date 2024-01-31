package ui.stations.my_trips.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import tj.ham_safar.app.trips.my_trips.data.remote.GetAllMyTripsClient
import tj.ham_safar.app.trips.my_trips.domain.GetAllMyTrips
import stations.my_trips.data.remote.GetAllMyTripsHttpClient

val MyTripsModule = module {

    factory<GetAllMyTripsClient> { GetAllMyTripsHttpClient(get(), get()) }

    factoryOf(::GetAllMyTrips)
}