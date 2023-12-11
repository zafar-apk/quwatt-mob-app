package ui.passengers.filter.di

//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//import dagger.hilt.android.scopes.ViewModelScoped
//import io.ktor.client.*
//import tj.ham_safar.app.passengers.filter.data.remote.AvailablePassengerTripsClient
//import tj.ham_safar.app.passengers.filter.data.remote.AvailablePassengerTripsHttpClient
//import tj.ham_safar.app.passengers.filter.domain.usecases.GetAvailablePassengerTrips
//
//@Module
//@InstallIn(ViewModelComponent::class)
//object PassengerTripFilterModule {
//
//    @Provides
//    @ViewModelScoped
//    fun provideAvailablePassengerTripsClient(
//        httpClient: HttpClient,
//    ): AvailablePassengerTripsClient =
//        AvailablePassengerTripsHttpClient(client = httpClient)
//
//    @Provides
//    @ViewModelScoped
//    fun provideGetAvailablePassengerTrips(client: AvailablePassengerTripsClient): GetAvailablePassengerTrips =
//        GetAvailablePassengerTrips(client = client)
//}