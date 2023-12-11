@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package ui.auth.presentation.enter_phone

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import auth.enter_phone.presentation.EnterPhoneScreenEvent
import auth.enter_phone.presentation.EnterPhoneViewModel
import core.domain.util.stringResource
import moe.tlaster.precompose.koin.koinViewModel
import tj.ham_safar.app.android.core.presentation.Routes
import tj.ham_safar.app.android.core.presentation.components.CustomTextField
import tj.ham_safar.app.android.core.presentation.components.Loader
import tj.yakroh.yakrohapp.SharedRes
import ui.core.presentation.components.MainButton

@Composable
fun EnterPhone(
    viewModel: EnterPhoneViewModel = koinViewModel(EnterPhoneViewModel::class),
    navigate: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = state) {
        if (state.next) {
            navigate("${Routes.ENTER_CODE}/${state.phone}")
            viewModel.onEvent(EnterPhoneScreenEvent.ResetNavigation)
        }
    }
    Column(modifier = Modifier.fillMaxSize().imePadding()) {
        Spacer(modifier = Modifier.weight(1F))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = SharedRes.strings.enter_phone_number),
                style = MaterialTheme.typography.h2
            )
            Spacer(modifier = Modifier.size(16.dp))
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 21.dp),
                onTextChanged = { viewModel.onEvent(EnterPhoneScreenEvent.OnPhoneTextChanged(it)) },
                placeholderText = stringResource(id = SharedRes.strings.phone_prefix),
                maxCount = 9,
                keyboardActions = KeyboardActions(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Phone
                )
            )
            if (state.error != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = state.error ?: "",
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
            onClick = { viewModel.onEvent(EnterPhoneScreenEvent.Continue) }
        )
        Spacer(modifier = Modifier.size(34.dp))
    }
    AnimatedVisibility(visible = state.isLoading) {
        Loader()
    }
}