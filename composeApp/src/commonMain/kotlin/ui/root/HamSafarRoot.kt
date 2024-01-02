package ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import auth.enter_code.presentation.EnterCodeScreenEvent
import auth.enter_code.presentation.EnterCodeViewModel
import com.mmk.kmpnotifier.notification.NotifierManager
import hamsafar_root.HamsafarRootEvent
import hamsafar_root.HamsafarRootViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import profile.presentation.ProfileScreenEvent
import profile.presentation.ProfileScreenState
import profile.presentation.ProfileScreenViewModel
import register.user.presentation.RegisterUserScreenEvent
import register.user.presentation.RegisterUserViewModel
import tj.ham_safar.app.android.core.presentation.Routes
import tj.ham_safar.app.trips.create.CompleteAction
import ui.auth.presentation.enter_code.EnterCode
import ui.auth.presentation.enter_phone.EnterPhone
import ui.core.presentation.changePlaceHoldersToArgs
import ui.core.presentation.components.HamsafarBottomNavigation
import ui.passengers.all.presentation.allPassengersRoute
import ui.passengers.my_requests.presentation.navigateToMyRequestsScreen
import ui.presentation.OnBoarding
import ui.profile.presentation.ProfileScreen
import ui.register.register_driver.addRegisterDriverGraph
import ui.register.register_driver.navigateToRegisterDriverGraph
import ui.register.user.presentation.user.RegisterUserScreen
import ui.root.Arguments.PhoneNumber
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

    NotifierManager.addListener(object : NotifierManager.Listener {
        override fun onNewToken(token: String) {
            viewModel.onEvent(HamsafarRootEvent.OnNewFcmToken(token))
        }
    })

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
    val scope = rememberCoroutineScope()
    val currentDestination by navigator.currentEntry.mapNotNull { it?.route?.route }
        .collectAsState(null)
    val popupTo: (String, Boolean) -> Unit = { route, inclusive ->
        scope.launch {
            while (currentDestination != route) {
                navigator.goBack()
                delay(500)
            }
            if (inclusive) {
                delay(500)
                navigator.goBack()
            }
        }
    }
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
            navController = navigator,
            popupTo = popupTo
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
    modifier: Modifier = Modifier,
    popupTo: (String, Boolean) -> Unit
) {
    NavHost(
        modifier = modifier,
        navigator = navController,
        initialRoute = allTripsRoute
    ) {
        addRegisterDriverGraph(navController)

        scene(route = Routes.ENTER_PHONE) {
            EnterPhone(
                navigateToEnterCode = { navController.navigate("${Routes.ENTER_CODE}/$it") },
                onNavigateUp = navController::goBack
            )
        }

        scene(route = "${Routes.ENTER_CODE}/{$PhoneNumber}") { backStackEntry ->
            val viewModel = koinViewModel(EnterCodeViewModel::class)
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(true) {
                viewModel.onEvent(
                    EnterCodeScreenEvent.OnPhoneAvailable(
                        backStackEntry.path<String>(PhoneNumber).orEmpty()
                    )
                )
            }

            EnterCode(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigateUp = navController::goBack,
                onForgotPassword = {
                    navController.navigate(Routes.FORGOT_PASSWORD)
                },
                onRegister = {
                    navController.navigate(Routes.REGISTER_USER)
                },
                onSuccessfullyAuthorized = {
                    navController.navigate(
                        Routes.PROFILE,
                        options = NavOptions(
                            popUpTo = PopUpTo.Route(
                                route = Routes.ENTER_PHONE,
                                inclusive = true
                            )
                        )
                    )
                }
            )
        }

        scene(route = Routes.REGISTER_USER) {
            val viewModel: RegisterUserViewModel = koinViewModel(RegisterUserViewModel::class)
            val state by viewModel.state.collectAsState()
            RegisterUserScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        RegisterUserScreenEvent.GoBack -> navController.goBack()
                        RegisterUserScreenEvent.CompleteRegistration ->
                            navController.goBack()

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }

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
        scene(route = Routes.PROFILE) {
            val viewModel = koinViewModel(ProfileScreenViewModel::class)
            val state by viewModel.state.collectAsState(ProfileScreenState())
            ProfileScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        ProfileScreenEvent.OnLogin ->
                            navController.navigate(
                                route = Routes.ENTER_PHONE,
                                options = NavOptions(
                                    popUpTo = PopUpTo(
                                        route = Routes.PROFILE,
                                        inclusive = true
                                    )
                                )
                            )

                        ProfileScreenEvent.NavigateToRegisterLicence -> navController.navigateToRegisterDriverGraph()
                        ProfileScreenEvent.NavigateToRegisterTransport -> navController.navigateToRegisterDriverGraph()
                        ProfileScreenEvent.NavigateToMyRequests -> navController.navigateToMyRequestsScreen()
//                        ProfileScreenEvent.NavigateToMyTrips -> navController.navigateToMyTripsScreen()
//                        ProfileScreenEvent.NavigateToTransport -> navController.navigateToEditTransportScreen()
//                        ProfileScreenEvent.NavigateToLicence -> navController.navigateToEditLicenceScreen()
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
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