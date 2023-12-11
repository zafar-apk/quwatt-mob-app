package tj.ham_safar.app.android.trips.chooseSpot.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.core.presentation.components.BackButton
import trips.all.choose_spot.presentation.ChooseSpotScreenEvent
import trips.all.choose_spot.presentation.ChooseSpotScreenState

@Composable
fun ChooseSpotScreen(
    state: ChooseSpotScreenState,
    onEvent: (ChooseSpotScreenEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .padding(horizontal = 21.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BackButton { onEvent(ChooseSpotScreenEvent.OnBackClick) }
                }
            }
        }
    }
}