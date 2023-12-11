package ui.trips.create.location.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import ui.core.presentation.components.BackButton
import ui.theme.Gray
import tj.ham_safar.app.trips.create.location.presentation.TripLocationEvent
import tj.ham_safar.app.trips.create.location.presentation.TripLocationScreenState
import ui.auth.presentation.components.BigStyleTextField
import ui.core.presentation.components.MainButton
import ui.trips.filter.presentation.components.TextDropDown

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TripLocationScreen(
    state: TripLocationScreenState,
    onEvent: (TripLocationEvent) -> Unit
) {
//    val context = LocalContext.current
    LaunchedEffect(state.shouldGoNext) {
        if (state.shouldGoNext) {
            onEvent(TripLocationEvent.NavigateToTheNextScreen)
            onEvent(TripLocationEvent.ResetState)
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            onEvent(TripLocationEvent.ResetState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp)
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButton(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        ) {
            onEvent(TripLocationEvent.GoBack)
        }


        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 36.dp),
            style = MaterialTheme.typography.subtitle1,
            fontSize = 48.sp,
            text = stringResource(id = "location_from")
        )

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 8.dp),
            style = MaterialTheme.typography.subtitle1,
            color = Gray,
            text = stringResource(id = "select_location_from")
        )

        TextDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            hint = "",
            elevation = 0.dp,
            optionsModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp),
            selectedText = state.cityFrom?.name.orEmpty(),
            isDropDownOpened = state.isPickingCityFrom,
            options = state.cities.orEmpty().map { it.name },
            onOpenDropDown = { onEvent(TripLocationEvent.PickCityFrom) },
            onSelectOption = { onEvent(TripLocationEvent.OnCityFromPicked(it)) },
            onDismiss = { onEvent(TripLocationEvent.OnCancelPickingCityFrom) }
        )

        BigStyleTextField(
            value = state.addressFrom.orEmpty(),
            hint = stringResource("enter_address"),
            onValueChanged = { onEvent(TripLocationEvent.AddressFromTyped(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        )

        FloatingActionButton(
            modifier = Modifier.padding(top = 24.dp),
            onClick = { onEvent(TripLocationEvent.SwapLocations) },
            backgroundColor = Color.White
        ) {
            Image(
                painter = painterResource("ic_arrow_swap.xml"),
                contentDescription = stringResource(id = "button_swap")
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 24.dp),
            style = MaterialTheme.typography.subtitle1,
            fontSize = 48.sp,
            text = stringResource(id = "location_to")
        )

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 12.dp),
            style = MaterialTheme.typography.subtitle1,
            color = Gray,
            text = stringResource(id = "select_location_to")
        )

        TextDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            hint = "",
            selectedText = state.cityTo?.name.orEmpty(),
            isDropDownOpened = state.isPickingCityTo,
            optionsModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp),
            elevation = 0.dp,
            options = state.cities.orEmpty().map { it.name },
            onOpenDropDown = { onEvent(TripLocationEvent.PickCityTo) },
            onSelectOption = { onEvent(TripLocationEvent.OnCityToPicked(it)) },
            onDismiss = { onEvent(TripLocationEvent.OnCancelPickingCityTo) }
        )

        BigStyleTextField(
            value = state.addressTo.orEmpty(),
            hint = stringResource("enter_address"),
            onValueChanged = { onEvent(TripLocationEvent.AddressToTyped(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        )


        MainButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 26.dp),
            labelRes = "next"
        ) {
            onEvent(TripLocationEvent.GoNext)
        }
    }
}

//@Preview
//@Composable
//private fun TripLocationScreenPreview() {
//    HamSafarTheme {
//        Surface(color = BackgroundGray) {
//            TripLocationScreen(
//                state = TripLocationScreenState()
//            ) {}
//        }
//    }
//}