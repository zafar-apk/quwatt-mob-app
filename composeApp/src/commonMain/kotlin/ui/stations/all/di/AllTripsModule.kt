package ui.stations.all.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import stations.all.data.remote.AllStationsClient
import stations.all.data.remote.AllStationsHttpClientClient
import stations.all.data.remote.mapper.StationsMapper
import stations.all.domain.use_case.GetAllStations
import stations.all.presentation.ChargingStationsScreenViewModel

val AllTripsModule = module {

    factoryOf(::StationsMapper)

    factory<AllStationsClient> {
        AllStationsHttpClientClient(
            client = get(),
            mapper = get()
        )
    }

    factoryOf(::GetAllStations)

    factoryOf(::ChargingStationsScreenViewModel)

}