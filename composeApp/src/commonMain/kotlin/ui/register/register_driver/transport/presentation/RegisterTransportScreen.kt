@file:OptIn(ExperimentalComposeUiApi::class)

package ui.register.register_driver.transport.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportColors
import profile.domain.transport.TransportType
import register.transport.presentation.RegisterTransportEvent
import register.transport.presentation.RegisterTransportScreenState
import tj.ham_safar.app.android.core.presentation.components.TopBar
import tj.ham_safar.app.android.theme.Blue
import tj.ham_safar.app.android.theme.Gray
import tj.ham_safar.app.android.theme.GrayGainsboro
import tj.ham_safar.app.android.theme.Red
import ui.auth.presentation.components.BigStyleTextField
import ui.core.presentation.components.MainButton
import ui.core.presentation.getImagePainterOrPlaceHolder
import ui.trips.filter.presentation.components.TextDropDown

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterTransportScreen(
    state: RegisterTransportScreenState,
    onEvent: (RegisterTransportEvent) -> Unit
) {
    val keyboardManager = LocalSoftwareKeyboardController.current
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = (ActivityResultContracts.GetContent()),
//        onResult = { uri ->
//            uri?.let { ImageFile(uri, context.contentResolver) }
//                ?.let(RegisterTransportEvent::OnTransportPhotoPicked)
//                ?.let(onEvent)
//        }
//    )
    LaunchedEffect(key1 = state) {
        if (state.isTransportRegistered) {
            onEvent(RegisterTransportEvent.NavigateToProfile)
        }
        onEvent(RegisterTransportEvent.ResetState)
    }
    Scaffold(
        topBar = {
            TopBar(text = stringResource(id = "transport")) {
                onEvent(RegisterTransportEvent.GoBack)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 21.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .fillMaxWidth()
                        .height(height = 160.dp)
                        .clickable {
//                            launcher.launch("image/jpeg")
                        },
                    painter = getImagePainterOrPlaceHolder(
//                        photo = state.photo?.uri,
                        photo = "ic_user.xml",
                        placeholderResId = "car_placeholder@1x.png"
                    ),
                    contentDescription = stringResource(id = "transport_photo")
                )
            }

            item {
                Text(
                    modifier = Modifier.clickable {
//                        launcher.launch("image/jpeg")
                    },
                    text = stringResource(id = "pick_photo"),
                    style = MaterialTheme.typography.body1,
                    color = Blue
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    text = stringResource(id = "auto_type"),
                    style = MaterialTheme.typography.subtitle1, color = Gray
                )
                TextDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .height(52.dp),
                    hint = "",
                    elevation = 0.dp,
                    selectedText = state.type.displayName,
                    isDropDownOpened = state.isPickingType,
                    options = TransportType.values().map(TransportType::displayName),
                    optionsModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp),
                    onOpenDropDown = { onEvent(RegisterTransportEvent.OnPickTransportType) },
                    onSelectOption = { typeName ->
                        TransportType.findByDisplayName(typeName)?.let { type ->
                            onEvent(RegisterTransportEvent.OnTransportTypePicked(type))
                        }
                    },
                    onDismiss = {
                        onEvent(RegisterTransportEvent.OnCancelPickingTransportType)
                    }
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(id = "transport_brand"),
                    style = MaterialTheme.typography.subtitle1,
                    color = Gray
                )

                TextDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .height(52.dp),
                    hint = "",
                    elevation = 0.dp,
                    selectedText = state.brand.displayName,
                    isDropDownOpened = state.isPickingBrand,
                    options = TransportBrand.values().map(TransportBrand::displayName),
                    optionsModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp),
                    onOpenDropDown = { onEvent(RegisterTransportEvent.OnPickTransportBrand) },
                    onSelectOption = { typeName ->
                        onEvent(
                            RegisterTransportEvent.OnTransportBrandPicked(
                                TransportBrand.findByDisplayName(typeName)
                            )
                        )
                    },
                    onDismiss = {
                        onEvent(RegisterTransportEvent.OnCancelPickingTransportBrand)
                    }
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(id = "transport_model"),
                    style = MaterialTheme.typography.subtitle1,
                    color = Gray
                )

                BigStyleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    onValueChanged = {
                        onEvent(RegisterTransportEvent.OnTransportModelChanged(it))
                    },
                    value = state.model.orEmpty(),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardManager?.hide()
                    }),
                    isError = state.modelIsNotEntered
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(id = "transport_color"),
                    style = MaterialTheme.typography.subtitle1,
                    color = Gray
                )

                TextDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .height(52.dp),
                    hint = "",
                    elevation = 0.dp,
                    selectedText = state.color.displayName,
                    isDropDownOpened = state.isPickingColor,
                    options = TransportColors.values().map(TransportColors::displayName),
                    optionsModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp),
                    onOpenDropDown = { onEvent(RegisterTransportEvent.OnPickTransportColor) },
                    onSelectOption = { typeName ->
                        TransportColors.findByDisplayName(typeName)?.let {
                            onEvent(RegisterTransportEvent.OnTransportColorPicked(it))
                        }
                    },
                    onDismiss = {
                        onEvent(RegisterTransportEvent.OnCancelPickingTransportColor)
                    }
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(id = "release_year"),
                    style = MaterialTheme.typography.subtitle1,
                    color = Gray
                )

                BigStyleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    onValueChanged = { yearRaw ->
                        yearRaw.toIntOrNull()?.let {
                            onEvent(RegisterTransportEvent.OnDateOfIssueChanged(it))
                        }
                    },
                    value = state.yearOfRelease?.toString().orEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardManager?.hide()
                    }),
                    isError = state.yearIfIssueIsNotEntered
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = "transport_capacity"),
                        style = MaterialTheme.typography.subtitle1,
                        color = Gray
                    )

                    CapacityField(onEvent, state, keyboardManager)

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(id = "ac"),
                        style = MaterialTheme.typography.subtitle1,
                        color = Gray
                    )

                    Checkbox(
                        modifier = Modifier
                            .padding(start = 2.dp),
                        checked = state.hasAC,
                        onCheckedChange = { onEvent(RegisterTransportEvent.OnHasACPicked(it)) }
                    )
                }
            }

            item {
                MainButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    labelRes = "save"
                ) {
                    onEvent(RegisterTransportEvent.Save)
                }
            }
        }
    }
}

@Composable
private fun CapacityField(
    onEvent: (RegisterTransportEvent) -> Unit,
    state: RegisterTransportScreenState,
    keyboardManager: SoftwareKeyboardController?
) {
    BasicTextField(
        onValueChange = { capacity ->
            onEvent(RegisterTransportEvent.OnCapacityPicked(capacity.toIntOrNull()))
        },
        value = state.capacity?.toString().orEmpty(),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardManager?.hide()
        }),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .height(53.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        color = Color.White,
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        width = 1.dp,
                        color = if (state.capacityIsNotPicked) Red else GrayGainsboro,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        }
    )
}

//@Preview
//@Composable
//private fun RegisterTransportScreenPreview() {
//    HamSafarTheme {
//        RegisterTransportScreen(state = RegisterTransportScreenState(), onEvent = {})
//    }
//}