package ui.stations.details.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import stations.detailed_trip.data.remote.StationDetailsClient
import stations.detailed_trip.data.remote.StationDetailsHttpClient
import stations.detailed_trip.domain.use_case.GetStationById
import stations.detailed_trip.presentation.StationDetailsViewModel

val StationDetailsModule = module {

    factoryOf(::StationDetailsHttpClient) bind (StationDetailsClient::class)

    factoryOf(::GetStationById)

    factoryOf(::StationDetailsViewModel)

}