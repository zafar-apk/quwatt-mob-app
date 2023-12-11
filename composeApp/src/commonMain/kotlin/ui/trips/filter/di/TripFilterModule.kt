package ui.trips.filter.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import tj.ham_safar.app.trips.filter.domain.usecases.GetAvailableTrips
import trips.filter.data.remote.AvailableTripsClient
import trips.filter.data.remote.AvailableTripsHttpClient
import trips.filter.presentation.TripFilterScreenViewModel

val TripFilterModule = module {

    factory<AvailableTripsClient> { AvailableTripsHttpClient(get(), get()) }


    factoryOf(::GetAvailableTrips)

    factoryOf(::TripFilterScreenViewModel)
}