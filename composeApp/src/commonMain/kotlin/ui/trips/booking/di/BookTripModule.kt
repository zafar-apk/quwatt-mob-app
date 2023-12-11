package ui.trips.booking.di


import io.ktor.client.*
import trips.booking.data.BookingTripClient
import trips.booking.data.BookingTripHttpClient
import trips.booking.domain.BookTrip

//@Module
//@InstallIn(ViewModelComponent::class)
//class BookTripModule {
//    @Provides
//    @ViewModelScoped
//    fun provideBookTripClient(httpClient: HttpClient): BookingTripClient =
//        BookingTripHttpClient(client = httpClient)
//
//    @Provides
//    @ViewModelScoped
//    fun provideBookTrip(detailedTripClient: BookingTripClient): BookTrip =
//        BookTrip(client = detailedTripClient)
//}