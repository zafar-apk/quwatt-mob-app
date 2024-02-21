package tj.quwatt.quwattapp

import App
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.domain.cities.model.City
import stations.all.domain.models.Connector
import stations.all.domain.models.ConnectorType
import stations.all.domain.models.Location
import stations.all.domain.models.Station
import stations.all.domain.models.TimeOfDay
import stations.all.presentation.StationsScreenState
import stations.detailed_trip.presentation.StationDetailsState
import ui.stations.all.presentation.AllStationsScreen
import ui.stations.details.presentation.StationDetailsScreen
import ui.stations.details.presentation.components.ConnectorItem
import ui.theme.AppTheme

@Composable
fun MainView() = App()

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true, showSystemUi = false
)
@Composable
fun Development() {
    AppTheme {
        val connector = Connector(
            id = 1,
            type = ConnectorType(
                id = 1,
                name = "GB/T",
                image = "https://cdnus.globalso.com/midaevse/GBT-Connector-1.jpg"
            ),
            power = 120,
            price = 130F,
            isAvailable = true
        )
        Column {

            Column {
                ConnectorItem(
                    connector = connector,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(8.dp))
                ConnectorItem(
                    connector = connector.copy(
                        isAvailable = false
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.size(32.dp))

            StationDetailsScreen(
                state = StationDetailsState(
                    station = Station(
                        id = 1,
                        isFavorite = false,
                        location = Location(
                            latitude = 0.0,
                            longitude = 0.0,
                            address = "Address",
                            city = City(
                                id = 1,
                                name = "City"
                            )
                        ),
                        images = emptyList(),
                        workingHours = "Working hours",
                        workingTimeOfDay = TimeOfDay.DAY_NIGHT,
                        rateFrom = 130F,
                        rateTo = null,
                        powerFrom = 120,
                        powerTo = null,
                        isAvailable = true,
                        chargersCount = 2
                    ),
                    connectors = listOf(
                        connector,
                        connector.copy(
                            id = 2,
                            type = ConnectorType(
                                id = 2,
                                name = "GB/T",
                                image = "https://cdnus.globalso.com/midaevse/GBT-Connector-1.jpg"
                            ),
                            power = 120,
                            price = 130F,
                            isAvailable = false
                        )
                    )
                ),
                onEvent = {},
                onAction = {}
            )

            Spacer(modifier = Modifier.size(32.dp))


        }

    }
}

@Preview
@Composable
private fun StationssALl() {
    AppTheme {
        AllStationsScreen(
            state = StationsScreenState(
                stations = listOf(
                    Station(
                        id = 1,
                        isFavorite = false,
                        location = Location(
                            latitude = 0.0,
                            longitude = 0.0,
                            address = "Address",
                            city = City(
                                id = 1,
                                name = "City"
                            )
                        ),
                        images = emptyList(),
                        workingHours = "Working hours",
                        workingTimeOfDay = TimeOfDay.DAY_NIGHT,
                        rateFrom = 130F,
                        rateTo = null,
                        powerFrom = 120,
                        powerTo = null,
                        isAvailable = true,
                        chargersCount = 2
                    ),
                    Station(
                        id = 2,
                        isFavorite = true,
                        location = Location(
                            latitude = 0.0,
                            longitude = 0.0,
                            address = "Address",
                            city = City(
                                id = 1,
                                name = "City"
                            )
                        ),
                        images = emptyList(),
                        workingHours = "Working hours",
                        workingTimeOfDay = TimeOfDay.DAY_NIGHT,
                        rateFrom = 130F,
                        rateTo = null,
                        powerFrom = 120,
                        powerTo = null,
                        isAvailable = true,
                        chargersCount = 2
                    )
                ),
            ),
            openStationsFilterForResult = { null },
            onEvent = {}
        )
    }
}
