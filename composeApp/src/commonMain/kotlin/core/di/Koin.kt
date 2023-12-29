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
import ui.register.register_driver.licence.di.RegisterLicenceModule
import ui.register.user.di.RegisterUserModule
import ui.root.RootModule
import ui.trips.all.di.AllTripsModule
import ui.trips.detailed_booking_trip.di.DetailedBookedTripModule
import ui.trips.detailed_trip.di.DetailedTripModule
import ui.trips.filter.di.TripFilterModule
import ui.trips.my_trips.di.MyTripsModule

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
            DetailedTripModule,
            CreateTripModule,
            DetailedBookedTripModule,
            EditLicenceModule,
            MyTripsModule,
            platformDataStoreModule,
            RegisterLicenceModule,
            RegisterUserModule
        )
    }
}

expect val platformDataStoreModule: Module