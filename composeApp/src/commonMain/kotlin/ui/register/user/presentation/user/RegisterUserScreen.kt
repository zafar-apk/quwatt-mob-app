@file:OptIn(ExperimentalComposeUiApi::class)

package ui.register.user.presentation.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.ImagePickerLauncher
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.domain.util.getCurrentDateTime
import core.domain.util.stringResource
import core.presentation.DatePickerDialog
import core.presentation.Toast
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import register.user.presentation.RegisterUserScreenEvent
import register.user.presentation.RegisterUserScreenState
import tj.ham_safar.app.android.register.user.presentation.user.components.LineTextField
import tj.yakroh.yakrohapp.SharedRes
import ui.core.presentation.components.BackButton
import ui.core.presentation.components.MainButton
import ui.theme.Blue
import ui.theme.Gray
import ui.theme.LightGray

@Composable
fun RegisterUserScreen(
    onEvent: (RegisterUserScreenEvent) -> Unit,
    state: RegisterUserScreenState
) {
    val keyboardManager = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val errorMessageMutableState: MutableState<String?> = remember {
        mutableStateOf(null)
    }

    val launcher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        resizeOptions = ResizeOptions(width = 800, height = 800),
        onResult = { byteArrays ->
            byteArrays.firstOrNull()
                ?.let(RegisterUserScreenEvent::OnUserPhotoPicked)
                ?.let(onEvent)
        }
    )

    LaunchedEffect(
        key1 = state.registrationCompleted,
        block = {
            if (state.registrationCompleted) {
                onEvent(RegisterUserScreenEvent.CompleteRegistration)
            }
        }
    )

    LaunchedEffect(state.error) {
        if (state.error != null) {
            errorMessageMutableState.value = state.error.message
        }
    }

    if (state.isPickingDateOfBirth) {
        DatePickerDialog(
            onDismissRequest = {
                onEvent(RegisterUserScreenEvent.ChangeDateOfBirthDatePickerState(isVisible = false))
            },
            onDateSelected = {
                onEvent(RegisterUserScreenEvent.OnDateOfBirthPicked(it))
            },
            validateDate = { date ->
                date == null || date < (getCurrentDateTime().date.minus(DatePeriod(years = 18)))
            },
            dateSeparator = "-"
        )
    }

    Toast(errorMessageMutableState)

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .hideKeyboardOnClick(keyboardManager),
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                BackButton(
                    modifier = Modifier.padding(horizontal = 21.dp)
                        .padding(vertical = 12.dp)
                ) { onEvent(RegisterUserScreenEvent.GoBack) }

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp),
                    text = stringResource(SharedRes.strings.personal_info),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { paddingValues ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
                .imePadding()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (state.photo == null) {
                    Image(
                        modifier = getUserImageModifier(launcher),
                        contentDescription = stringResource(SharedRes.strings.user_photo),
                        painter = painterResource(SharedRes.images.ic_user)
                    )
                } else {
                    Image(
                        modifier = getUserImageModifier(launcher),
                        contentDescription = stringResource(SharedRes.strings.user_photo),
                        bitmap = state.photo.toImageBitmap()
                    )
                }

                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = launcher::launch,
                    colors = ButtonDefaults.textButtonColors(contentColor = Blue)
                ) {
                    Text(text = stringResource(SharedRes.strings.add_photo))
                }
                Spacer(modifier = Modifier.size(10.dp))
                LineTextField(
                    modifier = Modifier.padding(horizontal = 27.dp),
                    label = stringResource(SharedRes.strings.name_label),
                    value = state.name,
                    onValueChange = { onEvent(RegisterUserScreenEvent.OnNameChanged(it)) },
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(onNext = {
                        keyboardManager?.hide()
                    })
                )
                Spacer(modifier = Modifier.size(14.dp))
                LineTextField(
                    modifier = Modifier.padding(horizontal = 27.dp),
                    label = stringResource(SharedRes.strings.sur_name_label),
                    value = state.surName,
                    onValueChange = { onEvent(RegisterUserScreenEvent.OnSurNameChanged(it)) },
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(onNext = {
                        keyboardManager?.hide()
                    })
                )
                Spacer(modifier = Modifier.size(14.dp))
                LineTextField(
                    modifier = Modifier.padding(horizontal = 27.dp),
                    label = stringResource(SharedRes.strings.patronymic_label),
                    value = state.patronymic,
                    onValueChange = { onEvent(RegisterUserScreenEvent.OnPatronymicChanged(it)) },
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            keyboardManager?.hide()
                        }
                    )
                )
                Spacer(modifier = Modifier.size(14.dp))
                DateOfBirthField(dateOfBirth = state.dateOfBirth) {
                    keyboardManager?.hide()
                    onEvent(
                        RegisterUserScreenEvent.ChangeDateOfBirthDatePickerState(isVisible = true)
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))
                MainButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp, vertical = 16.dp),
                    labelResource = SharedRes.strings.save
                ) {
                    onEvent(RegisterUserScreenEvent.Register)
                }
            }
        }
    }
}

private fun Modifier.hideKeyboardOnClick(keyboardManager: SoftwareKeyboardController?): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures {
            keyboardManager?.hide()
        }
    }
}

private fun ColumnScope.getUserImageModifier(launcher: ImagePickerLauncher) = Modifier
    .padding(top = 14.dp)
    .align(Alignment.CenterHorizontally)
    .size(150.dp)
    .clip(CircleShape)
    .background(LightGray)
    .clickable(onClick = launcher::launch)

@Composable
fun DateOfBirthField(
    dateOfBirth: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 27.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = stringResource(SharedRes.strings.date_of_birth_label),
            style = MaterialTheme.typography.subtitle1,
            color = Gray
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = dateOfBirth.ifEmpty { stringResource(SharedRes.strings.select_date_of_birth) },
            style = MaterialTheme.typography.subtitle1,
            color = Blue
        )
        Spacer(modifier = Modifier.size(2.dp))
        Divider(color = Blue)
    }
}

//@Preview
//@Composable
//fun RegisterUserScreenPreview() {
//    HamSafarTheme {
//        RegisterUserScreen(onEvent = {}, state = RegisterUserScreenState())
//    }
//}