package core.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import ui.auth.di.AuthModule
import ui.core.di.ImageCompressorModule
import ui.di.CitiesModule
import ui.di.CreateTripModule
import ui.di.NetworkModule
import ui.di.UserModule
import ui.edit.licence.di.EditLicenceModule
import ui.profile.di.ProfileModule
import ui.register.register_driver.licence.di.RegisterLicenceModule
import ui.register.register_driver.transport.di.RegisterTransportModule
import ui.register.user.di.RegisterUserModule
import ui.root.RootModule
import ui.stations.all.di.AllTripsModule
import ui.stations.detailed_booking_trip.di.DetailedBookedTripModule
import ui.stations.details.di.StationDetailsModule
import ui.stations.filter.di.TripFilterModule
import ui.stations.my_trips.di.MyTripsModule

fun startKoin() {
    initKoin {
        // no-op
    }
}

fun initKoin(appDeclaration: KoinAppDeclaration) {
    startKoin {
        appDeclaration()
        modules(
            NetworkModule,
            UserModule,
            RootModule,
            CitiesModule,
            AllTripsModule,
            AuthModule,
            ImageCompressorModule,
            TripFilterModule,
            StationDetailsModule,
            CreateTripModule,
            DetailedBookedTripModule,
            EditLicenceModule,
            MyTripsModule,
            platformDataStoreModule,
            RegisterLicenceModule,
            RegisterUserModule,
            ProfileModule,
            RegisterTransportModule
        )
    }
}

expect val platformDataStoreModule: Module