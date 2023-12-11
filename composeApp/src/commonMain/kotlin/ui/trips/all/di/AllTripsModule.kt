package ui.trips.all.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import trips.all.data.remote.AllTripsClient
import trips.all.data.remote.AllTripsHttpClient
import trips.all.data.remote.mapper.TripsMapper
import trips.all.domain.use_case.GetAllTrips
import trips.all.presentation.TripsScreenViewModel

val AllTripsModule = module {

    factoryOf(::TripsMapper)

    factory<AllTripsClient> {
        AllTripsHttpClient(
            client = get(),
            mapper = get()
        )
    }

    factoryOf(::GetAllTrips)

    factoryOf(::TripsScreenViewModel)

}