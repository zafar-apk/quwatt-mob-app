@file:OptIn(ExperimentalComposeUiApi::class)

package ui.auth.presentation.enter_code

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import auth.enter_code.presentation.EnterCodeNavigation
import auth.enter_code.presentation.EnterCodeScreenEvent
import auth.enter_code.presentation.EnterCodeScreenState
import dev.icerock.moko.resources.compose.stringResource
import tj.quwatt.quwattapp.SharedRes
import ui.auth.presentation.components.BigStyleTextField
import ui.core.presentation.components.BackButton
import ui.core.presentation.components.Loader
import ui.core.presentation.components.MainButton

private const val OTP_CODE_MAX_LENGTH = 4

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EnterCode(
    state: EnterCodeScreenState,
    onEvent: (EnterCodeScreenEvent) -> Unit,
    onNavigateUp: () -> Unit,
    onSuccessfullyAuthorized: () -> Unit,
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit
) {
    LaunchedEffect(key1 = state.navigation) {
        when (state.navigation) {
            EnterCodeNavigation.BACK -> onNavigateUp()
            EnterCodeNavigation.NEXT -> onSuccessfullyAuthorized()
            EnterCodeNavigation.FORGOT_PASSWORD -> onForgotPassword()
            EnterCodeNavigation.REGISTER -> onRegister()
            null -> Unit
        }
        state.navigation?.let {
            onEvent(EnterCodeScreenEvent.ResetNavigation)
        }
    }
    val keyboardManger = LocalSoftwareKeyboardController.current
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        BackButton(
            onClick = { onEvent(EnterCodeScreenEvent.GoBack) },
            modifier = Modifier.padding(
                start = 21.dp,
                top = 16.dp
            )
        )
    }) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().imePadding().padding(paddingValues)) {
            Spacer(modifier = Modifier.weight(0.5F))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(SharedRes.strings.enter_code, state.phone),
                    style = MaterialTheme.typography.h2
                )
                Spacer(modifier = Modifier.size(16.dp))
                CodeTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp),
                    value = state.code,
                    onValueChanged = {
                        onEvent(EnterCodeScreenEvent.OnCodeChanged(it))
                    },
                    onDone = { keyboardManger?.hide() }
                )
                if (state.error != null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = state.error,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1F))
            MainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 21.dp),
                labelResource = SharedRes.strings.next,
                onClick = { onEvent(EnterCodeScreenEvent.Continue) }
            )
            Spacer(modifier = Modifier.size(34.dp))
        }

    }
    AnimatedVisibility(visible = state.isLoading) {
        Loader()
    }
}

@Composable
private fun CodeTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    BigStyleTextField(
        modifier = modifier,
        value = value,
        fontSize = 18.sp,
        onValueChanged = onValueChanged,
        label = stringResource(SharedRes.strings.code),
        maxLength = OTP_CODE_MAX_LENGTH,
        textAlignment = TextAlign.Center,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        )
    )
}