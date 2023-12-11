package ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.painterResource
import hamsafar_root.HamsafarRootEvent
import hamsafar_root.HamsafarRootViewModel
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import tj.ham_safar.app.android.core.presentation.Routes
import tj.ham_safar.app.trips.create.CompleteAction
import tj.yakroh.yakrohapp.SharedRes
import ui.auth.presentation.enter_phone.EnterPhone
import ui.core.presentation.changePlaceHoldersToArgs
import ui.core.presentation.components.HamsafarBottomNavigation
import ui.passengers.all.presentation.allPassengersRoute
import ui.presentation.OnBoarding
import ui.register.register_driver.addRegisterDriverGraph
import ui.register.register_driver.navigateToRegisterDriverGraph
import ui.root.Arguments.TripId
import ui.trips.all.presentation.addToGraphAllTripsScreen
import ui.trips.all.presentation.allTripsRoute
import ui.trips.detailed_booking_trip.presentation.navigateToDetailedBookedTripScreen
import ui.trips.filter.presentation.addToGraphTripFilterScreen
import ui.trips.filter.presentation.navigateToTripFilterScreen

object Arguments {
    const val passengerId = "passengerId"
    const val Stage = "stage"
    const val PhoneNumber = "phoneNumber"
    const val TripId = "tripId"
    const val CompleteAction = "complete-action"
}

private val bottomNavigationRoutes = listOf(
    allTripsRoute,
    allPassengersRoute,
    Routes.MESSAGES,
    Routes.PROFILE
)

@Composable
fun HamSafarRoot() {
    val viewModel: HamsafarRootViewModel = koinViewModel(vmClass = HamsafarRootViewModel::class)
    val state = viewModel.state.collectAsState()

    if (state.value.isFirstLaunch == true) {
        OnBoarding(
            onOpenNextScreen = { viewModel.onEvent(HamsafarRootEvent.SetFirstLaunchFalse) }
        )
    } else if (state.value.isFirstLaunch == false) {
        AppContent()
    }
}

@Composable
fun AppContent() {
    val navigator = rememberNavigator()
    val navBackStackEntry by navigator.currentEntry.collectAsState(null)
    val currentDestination = navBackStackEntry?.route?.route
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (bottomNavigationRoutes.contains(currentDestination)) {
                HamsafarBottomNavigation(navController = navigator)
            }
        }
    ) { paddingValues ->
        AppNavigation(
            modifier = Modifier.padding(paddingValues),
            navController = navigator
        )
    }
}

//@Composable
//fun ScreenNameAnalytics(navController: Navigator) {
//    DisposableEffect(true) {
//        val analytics = Firebase.analytics
//        val listener = navController.OnDestinationChangedListener { _, destination, _ ->
//            analytics.logEvent(
//                FirebaseAnalytics.Event.SCREEN_VIEW,
//                bundleOf(FirebaseAnalytics.Param.SCREEN_NAME to destination.route)
//            )
//        }
//        navController.addOnDestinationChangedListener(listener)
//        onDispose { navController.removeOnDestinationChangedListener(listener) }
//    }
//}

@Composable
private fun AppNavigation(
    navController: Navigator,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navigator = navController,
        initialRoute = allTripsRoute
    ) {
        addRegisterDriverGraph(navController)
//
        scene(route = Routes.ENTER_PHONE) {
            EnterPhone { route ->
                navController.navigate(route)
            }
        }
//
//        scene(route = "${Routes.ENTER_CODE}/{$PhoneNumber}") {
//            EnterCode(
//                onNavigateUp = navController::goBack,
//                navigate = { route ->
////                  TODO popUpTo issue
//                    navController.navigate(
//                        route = route,
//                        options = NavOptions(
//                            popUpTo = PopUpTo(
//                                route = Routes.ENTER_PHONE,
//                                inclusive = true
//                            )
//                        )
//                    )
//                },
//                onSuccessfullyAuthorized = {
////                    navController.popBackStack(Routes.ENTER_PHONE) previous logic
//                    navController.navigate(
//                        route = Routes.ENTER_PHONE,
////                        options = NavOptions(
////                            popUpTo = PopUpTo(
////                                route = Routes.ENTER_PHONE,
////                                inclusive = true
////                            )
////                        )
//                    )
//                }
//            )
//        }
//
//        scene(route = Routes.REGISTER_USER) {
//            val viewModel: RegisterUserAndroidViewModel =
//                koinViewModel(RegisterUserAndroidViewModel::class)
//            val state by viewModel.state.collectAsState()
//            RegisterUserScreen(
//                state = state,
//                onEvent = { event ->
//                    when (event) {
//                        RegisterUserScreenEvent.GoBack -> navController.goBack()
//                        RegisterUserScreenEvent.CompleteRegistration ->
//                            navController.goBack()
//
//                        else -> viewModel.onEvent(event)
//                    }
//                }
//            )
//        }
//
//        scene(route = Routes.DETAILED_PASSENGER) { backStackEntry ->
//            val viewModel = koinViewModel(DetailedPassengerScreenAndroidViewModel::class)
//            LaunchedEffect(false) {
//                backStackEntry.path<Int>(Arguments.passengerId)?.let {
//                    viewModel.onEvent(DetailedPassengerScreenEvent.LoadPassenger(it))
//                }
//            }
//            val state by viewModel.state.collectAsState()
//            DetailedPassengerScreen(
//                state = state,
//                onEvent = { event ->
//                    when (event) {
//                        DetailedPassengerScreenEvent.OnBackClick -> navController.popBackStack()
//                        else -> viewModel.onEvent(event)
//                    }
//                }
//            )
//        }
//
//        scene(route = Routes.DETAILED_TRIP) { backStackEntry ->
//            val viewModel = koinViewModel(DetailedTripScreenAndroidViewModel::class)
//            LaunchedEffect(false) {
//                backStackEntry.path<Int>(TripId)?.let { id ->
//                    viewModel.onEvent(DetailedTripScreenEvent.LoadTrip(id))
//                }
//            }
//            val state by viewModel.state.collectAsState()
//            DetailedTripScreen(
//                state = state,
//                onEvent = { event ->
//                    when (event) {
//                        is DetailedTripScreenEvent.OnBackClick -> {
//                            navController.popBackStack()
//                        }
//
//                        is DetailedTripScreenEvent.NavigateToBooking -> {
//                            navController.setResultToCurrentBackStack(
//                                tripDataKey, TripDataParcelize(event.tripId, event.seatsIds)
//                            )
//                            navController.navigateToBookingScreen()
//                        }
//
//                        is DetailedTripScreenEvent.WriteMessage -> {
//
//                        }
//
//                        else -> viewModel.onEvent(event)
//                    }
//                })
//        }
//
//        scene(route = Routes.CHOOSE_SPOT) { backStackEntry ->
//            val viewModel = koinViewModel(ChooseSpotScreenAndroidViewModel::class)
//            LaunchedEffect(false) {
//                val tripId = backStackEntry.path<Int>(TripId)!!
//                viewModel.onEvent(ChooseSpotScreenEvent.LoadTrip(tripId))
//            }
//            val state by viewModel.state.collectAsState()
//            ChooseSpotScreen(
//                state = state,
//                onEvent = { event ->
//                    when (event) {
//                        is ChooseSpotScreenEvent.OnBackClick -> navController.popBackStack()
//                        else -> viewModel.onEvent(event)
//                    }
//                }
//            )
//        }
//
//        scene(route = Routes.PROFILE) {
//            val viewModel = koinViewModel(ProfileScreenAndroidViewModel::class)
//            val state by viewModel.state.collectAsState(ProfileScreenState())
//            ProfileScreen(
//                state = state,
//                onEvent = { event ->
//                    when (event) {
//                        ProfileScreenEvent.NavigateToRegisterUser ->
//                            navController.navigate(
//                                route = Routes.REGISTER_USER,
//                                options = NavOptions(
//                                    popUpTo = PopUpTo(
//                                        route = Routes.PROFILE,
//                                        inclusive = true
//                                    )
//                                )
//                            )
//
//                        ProfileScreenEvent.OnLogin ->
//                            navController.navigate(
//                                route = Routes.ENTER_PHONE,
//                                options = NavOptions(
//                                    popUpTo = PopUpTo(
//                                        route = Routes.PROFILE,
//                                        inclusive = true
//                                    )
//                                )
//                            )
//
//                        ProfileScreenEvent.NavigateToRegisterLicence -> navController.navigateToRegisterDriverGraph()
//                        ProfileScreenEvent.NavigateToRegisterTransport -> navController.navigateToRegisterDriverGraph()
//                        ProfileScreenEvent.NavigateToMyRequests -> navController.navigateToMyRequestsScreen()
//                        ProfileScreenEvent.NavigateToMyTrips -> navController.navigateToMyTripsScreen()
//                        ProfileScreenEvent.NavigateToTransport -> navController.navigateToEditTransportScreen()
//                        ProfileScreenEvent.NavigateToLicence -> navController.navigateToEditLicenceScreen()
//                        else -> viewModel.onEvent(event)
//                    }
//                }
//            )
//        }
//
//
//        scene(route = Routes.CREATE_TRIP_LOCATION) { backStackEntry ->
//            val completeNavigation = backStackEntry.path<Int>(Arguments.CompleteAction) ?: 0
//            val viewModel: TripLocationAndroidViewModel =
//                koinViewModel(TripLocationAndroidViewModel::class)
//            val state by viewModel.state.collectAsState()
//            TripLocationScreen(state = state) { event ->
//                when (event) {
//                    TripLocationEvent.GoBack -> navController.goBack()
//                    TripLocationEvent.NavigateToTheNextScreen -> navController.navigate(
//                        Routes.createTripDateTime(completeNavigation)
//                    )
//
//                    else -> viewModel.onEvent(event)
//                }
//            }
//        }
//
//        scene(route = Routes.CREATE_TRIP_DATE_TIME) { backStackEntry ->
//            val completeNavigation = backStackEntry.path<Int>(Arguments.CompleteAction) ?: 0
//            val viewModel: TripDateTimeAndroidViewModel =
//                koinViewModel(TripDateTimeAndroidViewModel::class)
//            val state by viewModel.state.collectAsState()
//            TripDateTimeScreen(state = state) { event ->
//                when (event) {
//                    TripDateTimeScreenEvent.GoBack -> navController.goBack()
//                    TripDateTimeScreenEvent.NavigateToTheNextScreen ->
//                        when (completeNavigation) {
//                            CompleteAction.ActionCreateTrip ->
//                                navController.navigateToTripDriverScreen(completeNavigation)
//
//                            CompleteAction.ActionCreatePassengerRequest ->
//                                navController.navigateToTripPassengerScreen(completeNavigation)
//                        }
//
//                    else -> viewModel.onEvent(event)
//                }
//            }
//        }
//
//        addToGraphDetailedBookedTripScreen(
//            onGoBack = { navController.goBack() }
//        )
//
//        addToGraphBookingTripScreen(
//            onGoBack = {
//                // TODO
//                navController.navigate(Routes.DETAILED_TRIP)
//            },
////            onTripDataResult = navController.getResultFromPreviousBackStack(tripDataKey)
//        )
//
        addToGraphAllTripsScreen(
            onNavigateToCreateTrip = {
                navController.navigate(Routes.createTripLocation(CompleteAction.ActionCreateTrip))
            },
            onNavigateToTripsFilterForResult = {
                navController.navigateToTripFilterScreen()
            },
            onNavigateToTripItem = { tripItemId ->
                navController.navigate(
                    Routes.DETAILED_TRIP.changePlaceHoldersToArgs(TripId to tripItemId.toString())
                )
            },
            onNavigateToBookedTripItem = { bookedTripId ->
                navController.navigateToDetailedBookedTripScreen(
                    bookedTripId
                )
            },
            onNavigateToLogin = { navController.navigate(Routes.ENTER_PHONE) },
            onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
            onNavigateToLicence = { navController.navigateToRegisterDriverGraph() },
        )

        addToGraphTripFilterScreen(
            onGoBack = { navController.goBack() },
            onGoBackWithResult = navController::goBackWith
        )
//
//        addToGraphAllPassengersScreen(
//            onNavigateToFilter = { navController.navigateToPassengerFilterScreen() },
//            onNavigateToLogin = { navController.navigate(Routes.ENTER_PHONE) },
//            onNavigateToLeaveRequest = {
//                navController.navigate(
//                    Routes.createTripLocation(CompleteAction.ActionCreatePassengerRequest)
//                )
//            },
//            onNavigateToPassengerDetails = { passengerId ->
//                navController.navigate(
//                    Routes.DETAILED_PASSENGER.changePlaceHoldersToArgs(
//                        Arguments.passengerId to passengerId.toString()
//                    )
//                )
//            },
//            onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
////            onGetFilterResult = navController.getResultFromCurrentBackStack(
////                allPassengersFilterKey
////            )
//        )
//
//        addToGraphPassengerFilterScreen(
//            onGoBack = { navController.goBack() },
//            onGoBackWithResult = { filter ->
//                navController.setResultToPreviousBackStack(allPassengersFilterKey, filter)
//                navController.goBack()
//            },
////            onFilterResultReturned = {
////                navController.getResultFromPreviousBackStack(
////                    allPassengersFilterKey
////                )
////            }
//        )
//
//
//        addToGraphTripPassengerScreen(
//            onGoBack = { route ->
//                if (route == null) navController.popBackStack()
////                else navController.popBackStack(route, inclusive = false)
//            },
//            onSuccessfullyRequestLeft = {
////                navController.popBackStack(allPassengersRoute, inclusive = false)
//            }
//        )
//
//        addToGraphTripDriverScreen(
//            onGoBack = { route ->
//                if (route == null) navController.popBackStack()
//                else {
////                    navController.popBackStack(route, inclusive = false)
//                }
//            },
//            onSuccessfullyTripCreated = {
////                navController.popBackStack(allTripsRoute, inclusive = false)
//            }
//        )
//
//        addToGraphMyRequestsScreen(
//            onNavigateToPassengerDetail = { passengerId ->
//                navController.navigate(
//                    Routes.DETAILED_PASSENGER.replace(
//                        "{${Arguments.passengerId}}",
//                        passengerId.toString()
//                    )
//                )
//            },
//            onGoBack = { navController.goBack() }
//        )
//
//        addToGraphMyTripsScreen(
//            onNavigateToTripDetail = { tripId ->
//                navController.navigate(
//                    Routes.DETAILED_TRIP.replace(
//                        "{${Arguments.TripId}}",
//                        tripId.toString()
//                    )
//                )
//            },
//            onGoBack = { navController.goBack() }
//        )
//
//        addToGraphEditTransportScreen(
//            onGoBack = { navController.goBack() }
//        )
//
//        addToGraphEditLicenceScreen(
//            onGoBack = { navController.goBack() }
//        )
    }
}