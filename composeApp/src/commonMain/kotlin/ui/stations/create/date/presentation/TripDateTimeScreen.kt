package ui.stations.create.date.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.auth.presentation.components.BigStyleTextField
import ui.core.presentation.components.BackButton
import ui.core.presentation.components.MainButton
import tj.ham_safar.app.trips.create.date_time.presentation.TripDateTimeScreenEvent
import tj.ham_safar.app.trips.create.date_time.presentation.TripDateTimeScreenState
import ui.core.presentation.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TripDateTimeScreen(
    state: TripDateTimeScreenState,
    onEvent: (TripDateTimeScreenEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
//    val context = LocalContext.current

    val dateVisualTransformation = remember {
        DateVisualTransformation()
    }
    val timeVisualTransformation = remember {
        TimeVisualTransformation()
    }

    LaunchedEffect(state.shouldGoNext) {
        if (state.shouldGoNext) {
            onEvent(TripDateTimeScreenEvent.NavigateToTheNextScreen)
            onEvent(TripDateTimeScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            onEvent(TripDateTimeScreenEvent.ResetState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButton(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        ) {
            onEvent(TripDateTimeScreenEvent.GoBack)
        }

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource("illustration_picking_date_time.xml"),
            contentDescription = stringResource(id = "pick_date_time")
        )

        Text(
            text = state.title.orEmpty(),
            style = MaterialTheme.typography.subtitle1,
            fontSize = 24.sp
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = stringResource(id = "trip_date_label"),
            style = MaterialTheme.typography.h2
        )

        BigStyleTextField(
            hint = "09/05/2023",
            value = state.date.orEmpty(),
            onValueChanged = {
                onEvent(TripDateTimeScreenEvent.OnDateChanged(it))
            },
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            visualTransformation = dateVisualTransformation,
            maxLength = 8,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = stringResource(id = "trip_time_label"),
            style = MaterialTheme.typography.h2
        )

        BigStyleTextField(
            hint = "08:00",
            value = state.time.orEmpty(),
            onValueChanged = {
                onEvent(TripDateTimeScreenEvent.OnTimeChanged(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(force = true)
                }
            ),
            visualTransformation = timeVisualTransformation,
            maxLength = 4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        MainButton(
            modifier = Modifier.fillMaxWidth(),
            labelRes = "next"
        ) {
            onEvent(TripDateTimeScreenEvent.GoNext)
        }

        Spacer(modifier = Modifier.size(41.dp))
    }
}

//@Preview
//@Composable
//private fun TripDateScreenPreview() {
//    HamSafarTheme {
//        Surface(color = BackgroundGray) {
//            TripDateTimeScreen(state = TripDateTimeScreenState(), onEvent = {})
//        }
//    }
//}