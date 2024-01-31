package ui.di


import core.domain.util.DateTimeFormatter
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import tj.ham_safar.app.trips.core.create_trip.domain.CreateTripRepository
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.CreateTripUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripDateTimeUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripLocationUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripPricingUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripDateTimeUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripLocationUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripPricingUseCase
import stations.core.create_trip.data.CreateTripRepositoryImpl
import stations.core.create_trip.data.remote.CreateTripDriverClient
import stations.core.create_trip.data.remote.CreateTripDriverHttpClient
import stations.core.create_trip.data.remote.CreateTripPassengerClient
import stations.core.create_trip.data.remote.CreateTripPassengerHttpClient

val CreateTripModule = module {

    factory<CreateTripRepository> { CreateTripRepositoryImpl(get(), get()) }

    factory<CreateTripDriverClient> { CreateTripDriverHttpClient(get()) }

    factory<CreateTripPassengerClient> { CreateTripPassengerHttpClient(client = get()) }

    factoryOf(::GetTripLocationUseCase)

    factoryOf(::SetTripLocationUseCase)

    factoryOf(::GetTripDateTimeUseCase)

    factoryOf(::SetTripDateTimeUseCase)

    factoryOf(::DateTimeFormatter)

    factoryOf(::GetTripPricingUseCase)

    factoryOf(::SetTripPricingUseCase)

    factoryOf(::CreateTripUseCase)
}