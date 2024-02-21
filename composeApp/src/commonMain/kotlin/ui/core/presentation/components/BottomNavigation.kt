package ui.core.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveNavigationBar
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import tj.ham_safar.app.android.core.presentation.Routes
import tj.quwatt.quwattapp.SharedRes
import ui.passengers.all.presentation.allPassengersRoute
import ui.stations.all.presentation.allStationsRoute

@OptIn(ExperimentalResourceApi::class)
@Composable
fun QuWattBottomNavigation(navController: Navigator) {
    val items = listOf(
        Screen.Stations,
        Screen.Favorites,
        Screen.Profile
    )
    AdaptiveNavigationBar {
        val navBackStackEntry by navController.currentEntry.collectAsState(null)
        val currentDestination = navBackStackEntry?.route?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.height(28.dp),
                        painter = painterResource(screen.iconResource),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.labelResource),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
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

sealed class Screen(
    val route: String,
    val labelResource: StringResource,
    val iconResource: ImageResource
) {
    data object Stations :
        Screen(allStationsRoute, SharedRes.strings.stations, SharedRes.images.ic_lightnings)

    data object Favorites :
        Screen(allPassengersRoute, SharedRes.strings.favorites, SharedRes.images.ic_heart)

    data object Profile : Screen(Routes.PROFILE, SharedRes.strings.profile, SharedRes.images.ic_profile)
}