package ui.trips.detailed_booking_trip.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import tj.ham_safar.app.trips.detailed_booking_trip.data.remote.CancelBookedTripHttpClient
import tj.ham_safar.app.trips.detailed_booking_trip.data.remote.DetailedBookedTripHttpClient
import tj.ham_safar.app.trips.detailed_booking_trip.data.remote.mapper.DetailedBookedTripMapper
import tj.ham_safar.app.trips.detailed_booking_trip.domain.use_case.CancelBookedTrip
import tj.ham_safar.app.trips.detailed_booking_trip.domain.use_case.GetBookedTripById

val DetailedBookedTripModule = module {

    factoryOf(::DetailedBookedTripHttpClient)

    factoryOf(::CancelBookedTripHttpClient)

    factoryOf(::GetBookedTripById)

    factoryOf(::CancelBookedTrip)

    factoryOf(::DetailedBookedTripMapper)
}