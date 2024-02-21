package ui.register.register_driver.licence.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.presentation.DatePickerDialog
import core.presentation.Toast
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import register.licence.presentation.RegisterUserLicenceEvent
import register.licence.presentation.RegisterUserLicenceScreenState
import ui.core.presentation.components.TopBar
import tj.quwatt.quwattapp.SharedRes
import ui.auth.presentation.components.BigStyleTextField
import ui.core.presentation.components.MainButton
import ui.theme.GrayGainsboro
import ui.theme.Red
import ui.theme.TextBlack

private const val API_TYPE_DATE_SEPARATOR = "-"

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
@Composable
fun RegisterLicenceScreen(
    onEvent: (RegisterUserLicenceEvent) -> Unit,
    state: RegisterUserLicenceScreenState
) {
    val keyboardManager = LocalSoftwareKeyboardController.current

    val toastMessage: MutableState<String?> = remember {
        mutableStateOf(null)
    }
    Toast(messageState = toastMessage)

    LaunchedEffect(
        key1 = state.isLicenceRegistered,
        key2 = state.error,
        block = {
            state.error?.message?.let { message ->
                toastMessage.value = message
            }
            if (state.isLicenceRegistered) {
                onEvent(RegisterUserLicenceEvent.NavigateToRegisterTransport)
            }
            onEvent(RegisterUserLicenceEvent.ResetState)
        }
    )


    if (state.isPickingDocumentDate) {
        DatePickerDialog(
            onDismissRequest = {
                onEvent(RegisterUserLicenceEvent.DismissDocumentDatePicker)
            },
            onDateSelected = { selectedDate ->
                onEvent(RegisterUserLicenceEvent.OnExpirationDateChanged(selectedDate))
            },
            dateSeparator = API_TYPE_DATE_SEPARATOR
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(SharedRes.strings.driver_licence),
                onBackButtonClick = { onEvent(RegisterUserLicenceEvent.GoBack) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.weight(1F))

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                painter = painterResource(SharedRes.images.illustration_driving_license),
                contentDescription = stringResource(SharedRes.strings.illustration_driver_licence)
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(SharedRes.strings.enter_driving_licence_data),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(SharedRes.strings.serial_number),
                style = MaterialTheme.typography.subtitle1
            )

            BigStyleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onValueChanged = {
                    onEvent(RegisterUserLicenceEvent.OnLicenceNumberChanged(it))
                },
                value = state.licenceNumber,
                label = "1234567",
                maxLength = 7,
                phonePrefix = "AA",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { keyboardManager?.hide() }),
                isError = state.isLicenceNumberIsNotEntered
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(SharedRes.strings.expiration_date),
                style = MaterialTheme.typography.subtitle1
            )

            PickDateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                hint = "12/12/1234",
                text = state.expirationDate,
                onClick = { onEvent(RegisterUserLicenceEvent.OpenDocumentExpirationDatePicker) },
                isError = state.isExpirationDateIsNotEntered
            )

            Spacer(modifier = Modifier.weight(1F))

            MainButton(
                labelResource = SharedRes.strings.next,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp)
            ) {
                onEvent(RegisterUserLicenceEvent.GoNext)
            }
        }
    }
}

@Composable
private fun PickDateTextField(
    modifier: Modifier = Modifier,
    hint: String = "",
    text: String = "",
    onClick: () -> Unit = {},
    isError: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
            .border(
                width = 0.5.dp,
                color = if (isError) Red else GrayGainsboro,
                shape = MaterialTheme
                    .shapes
                    .medium
            )
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            text = text.ifEmpty { hint },
            style = MaterialTheme.typography.subtitle1,
            color = if (text.isEmpty()) GrayGainsboro else TextBlack
        )
    }
}

//@Preview
//@Composable
//private fun DatePickerPreview() {
//    HamSafarTheme {
//        DatePicker(
//            modifier = Modifier.fillMaxWidth(),
//            hint = "12/12/1234"
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun RegisterLicenceScreenPreview() {
//    HamSafarTheme {
//        RegisterLicenceScreen(
//            onEvent = {},
//            state = RegisterUserLicenceScreenState()
//        )
//    }
//}