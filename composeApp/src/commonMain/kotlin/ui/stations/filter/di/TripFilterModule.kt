package ui.stations.filter.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import tj.ham_safar.app.trips.filter.domain.usecases.GetAvailableTrips
import stations.filter.data.remote.AvailableTripsClient
import stations.filter.data.remote.AvailableTripsHttpClient
import stations.filter.presentation.TripFilterScreenViewModel

val TripFilterModule = module {

    factory<AvailableTripsClient> { AvailableTripsHttpClient(get(), get()) }


    factoryOf(::GetAvailableTrips)

    factoryOf(::TripFilterScreenViewModel)
}