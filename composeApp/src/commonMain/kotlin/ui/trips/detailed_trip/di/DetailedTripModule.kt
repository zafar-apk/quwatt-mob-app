package ui.trips.detailed_trip.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import tj.ham_safar.app.trips.detailed_trip.data.remote.DetailedTripHttpClient
import tj.ham_safar.app.trips.detailed_trip.domain.use_case.GetTripById

val DetailedTripModule = module {

    factoryOf(::DetailedTripHttpClient)

    factoryOf(::GetTripById)

}