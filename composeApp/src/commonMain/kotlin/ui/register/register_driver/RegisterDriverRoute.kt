package ui.register.register_driver

import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import ui.register.register_driver.licence.presentation.addToGraphRegisterLicenceScreen
import ui.register.register_driver.licence.presentation.registerLicenceRoute
import ui.register.register_driver.transport.presentation.addToGraphRegisterTransportScreen
import ui.register.register_driver.transport.presentation.navigateToRegisterTransportScreen

private const val registerDriver = "register-driver"

fun RouteBuilder.addRegisterDriverGraph(navController: Navigator) {
    group(
        route = registerDriver,
        initialRoute = registerLicenceRoute
    ) {
        addToGraphRegisterLicenceScreen(
            onGoBack = { navController.goBack() },
            onGoNext = { navController.navigateToRegisterTransportScreen() }
        )
        addToGraphRegisterTransportScreen(
            onGoBack = { navController.goBack() },
            onTransportRegistered = {
                // TODO
//                navController.popBackStack(registerLicenceRoute, true)
            }
        )
    }
}

fun Navigator.navigateToRegisterDriverGraph() = navigate(route = registerDriver)