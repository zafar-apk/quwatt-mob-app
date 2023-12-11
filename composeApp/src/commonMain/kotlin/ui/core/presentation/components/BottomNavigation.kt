package ui.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import tj.ham_safar.app.android.core.presentation.Routes
import tj.ham_safar.app.android.theme.Gray
import tj.ham_safar.app.android.theme.Yellow
import ui.passengers.all.presentation.allPassengersRoute
import ui.trips.all.presentation.allTripsRoute

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HamsafarBottomNavigation(navController: Navigator) {
    val items = listOf(
        Screen.Trips,
        Screen.Passengers,
//        Screen.Messages,
        Screen.Profile
    )
    BottomNavigation(backgroundColor = Color.White) {
        val navBackStackEntry by navController.currentEntry.collectAsState(null)
        val currentDestination = navBackStackEntry?.route?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(screen.iconRes),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.labelId),
                        fontSize = 12.sp
                    )
                },
                selectedContentColor = Yellow,
                unselectedContentColor = Gray,
                selected = currentDestination == screen.route,
//                currentDestination?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route)
                    // TODO handle navigation options
//                    {
//                        // Pop up to the start destination of the graph to
//                        // avoid building up a large stack of destinations
//                        // on the back stack as users select items
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        // Avoid multiple copies of the same destination when
//                        // reselecting the same item
//                        launchSingleTop = true
//                        // Restore state when reselecting a previously selected item
//                        restoreState = false
//                    }
                }
            )
        }
    }
}

//@Preview
//@Composable
//fun BottomNavPreview() {
//    HamSafarTheme {
//        HamsafarBottomNavigation(navController = rememberNavController())
//    }
//}

sealed class Screen(val route: String, val labelId: String, val iconRes: String) {
    object Trips : Screen(allTripsRoute, "trips", "ic_orders.xml")
    object Passengers : Screen(allPassengersRoute, "passengers", "ic_profile.xml")
    object Messages : Screen(Routes.MESSAGES, "messages", "ic_chat.xml")
    object Profile : Screen(Routes.PROFILE, "profile", "ic_profile.xml")
}