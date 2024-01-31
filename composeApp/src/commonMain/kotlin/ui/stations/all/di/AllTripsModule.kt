package ui.stations.all.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import stations.all.data.remote.AllTripsClient
import stations.all.data.remote.AllTripsHttpClient
import stations.all.data.remote.mapper.TripsMapper
import stations.all.domain.use_case.GetAllStations
import stations.all.presentation.ChargingStationsScreenViewModel

val AllTripsModule = module {

    factoryOf(::TripsMapper)

    factory<AllTripsClient> {
        AllTripsHttpClient(
            client = get(),
            mapper = get()
        )
    }

    factoryOf(::GetAllStations)

    factoryOf(::ChargingStationsScreenViewModel)

}