package ui.profile.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import profile.domain.User
import profile.presentation.ProfileScreenEvent
import profile.presentation.ProfileScreenState
import tj.ham_safar.app.android.core.presentation.components.Loader
import tj.ham_safar.app.android.core.presentation.components.TopBar
import ui.register.user.presentation.user.DateOfBirthField
import tj.ham_safar.app.android.register.user.presentation.user.components.LineTextField
import ui.theme.LightGray
import ui.core.presentation.components.ErrorView
import ui.core.presentation.components.LoginView
import ui.core.presentation.getImagePainterOrPlaceHolder
import ui.trips.components.ActionItem

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(
    state: ProfileScreenState,
    onEvent: (ProfileScreenEvent) -> Unit
) {
//    val context = LocalContext.current
    LaunchedEffect(key1 = state) {
        with(state) {
            when {
                shouldRegisterUser -> onEvent(ProfileScreenEvent.NavigateToRegisterUser)
                shouldRegisterLicence -> onEvent(ProfileScreenEvent.NavigateToRegisterLicence)
                shouldRegisterTransport -> onEvent(ProfileScreenEvent.NavigateToRegisterTransport)
                shouldOpenTransport -> onEvent(
                    ProfileScreenEvent.NavigateToTransport
                )

                shouldOpenLicence -> onEvent(
                    ProfileScreenEvent.NavigateToLicence
                )

                shouldOpenMyRequests -> onEvent(ProfileScreenEvent.NavigateToMyRequests)
                shouldOpenMyTrips -> onEvent(ProfileScreenEvent.NavigateToMyTrips)
                compressedImageNull -> {
//                    Toast.makeText(context, "compressedImageNull, Toast.LENGTH_SHORT).show()
                }
            }
            if (shouldReset) {
                onEvent(ProfileScreenEvent.ResetState)
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                text = stringResource(id = "profile"),
                backButtonEnabled = false,
                onBackButtonClick = { /* no-op */ }
            )
        }
    ) { contentPadding ->
        state.user?.let {
            LazyColumn(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(horizontal = 18.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                item {
                    UserInfo(
                        modifier = Modifier.fillMaxWidth(),
                        user = state.user,
                        onEvent = onEvent
                    )
                }

                item {
                    ActionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        actionName = stringResource(id = "transport"),
                        actionIcon = painterResource("ic_arrow_thin.xml"),
                        actionIconContentDescription = stringResource(id = "arrow_right"),
                        onAction = { onEvent(ProfileScreenEvent.OpenTransport) }
                    )
                }

                item {
                    if (state.user?.transport != null) ActionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        actionName = stringResource(id = "driver_licence"),
                        actionIcon = painterResource("ic_arrow_thin.xml"),
                        actionIconContentDescription = stringResource(id = "arrow_right"),
                        onAction = { onEvent(ProfileScreenEvent.OpenLicence) }
                    )
                }

                if (state.user?.isDriver == true) item {
                    ActionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        actionName = stringResource(id = "my_trips"),
                        actionIcon = painterResource("ic_list_item.xml"),
                        actionIconContentDescription = stringResource(id = "my_trips"),
                        onAction = { onEvent(ProfileScreenEvent.OpenMyTrips) }
                    )
                }

                item {
                    ActionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        actionName = stringResource(id = "my_requests"),
                        actionIcon = painterResource("ic_people.xml"),
                        actionIconContentDescription = stringResource(id = "my_requests"),
                        onAction = { onEvent(ProfileScreenEvent.OpenMyRequests) }
                    )
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        onClick = { onEvent(ProfileScreenEvent.Logout) }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp)
                        ) {
                            Text(
                                modifier = Modifier.align(Center),
                                text = stringResource(id = "logout"),
                                style = MaterialTheme.typography.h3
                            )
                        }
                    }
                }

            }
        }

        state.error?.let { error ->
            ErrorView(
                modifier = Modifier
                    .padding(contentPadding),
                error = error,
                onRetry = { onEvent(ProfileScreenEvent.LoadUser) }
            )
        }

        if (state.isLoading) Loader()


        if (state.isNotAuthorized) {
            LoginView(
                modifier = Modifier.fillMaxSize(),
                onLoginClicked = { onEvent(ProfileScreenEvent.OnLogin) }
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun UserInfo(
    user: User?,
    onEvent: (ProfileScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract = (ActivityResultContracts.GetContent()),
//        onResult = { uri ->
//            uri?.let { ImageFile(uri, context.contentResolver) }
//                ?.let { onEvent(ProfileScreenEvent.ChangeProfilePhoto(it)) }
//        }
//    )

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.size(12.dp))

            Box(modifier = Modifier.align(CenterHorizontally)) {
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(LightGray),
                    painter = getImagePainterOrPlaceHolder(
                        photo = user?.photo,
                        placeholderResId = "ic_user.xml"
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = "user_photo")
                )

                Image(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .clickable {
//                            launcher.launch("image/jpeg")
                        },
                    painter = painterResource("ic_edit.xml"),
                    contentDescription = stringResource(id = "edit")
                )
            }

            Spacer(modifier = Modifier.size(35.dp))

            LineTextField(
                modifier = Modifier.padding(horizontal = 27.dp),
                label = stringResource(id = "name_label"),
                value = user?.name.orEmpty(),
                onValueChange = { /* no-op */ },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(),
                isEditable = false
            )

            Spacer(modifier = Modifier.size(14.dp))

            LineTextField(
                modifier = Modifier.padding(horizontal = 27.dp),
                label = stringResource(id = "sur_name_label"),
                value = user?.surname.orEmpty(),
                onValueChange = { /* no-op */ },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(),
                isEditable = false
            )

            Spacer(modifier = Modifier.size(14.dp))

            LineTextField(
                modifier = Modifier.padding(horizontal = 27.dp),
                label = stringResource(id = "patronymic_label"),
                value = user?.patronymic.orEmpty(),
                onValueChange = { /* no-op */ },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(),
                isEditable = false
            )

            Spacer(modifier = Modifier.size(14.dp))

            DateOfBirthField(dateOfBirth = user?.dateOfBirth.orEmpty()) {
                /* no-op */
            }

            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

//@Preview
//@Composable
//private fun ProfileScreenPreview() {
//    HamSafarTheme {
//        ProfileScreen(
//            state = ProfileScreenState(),
//            onEvent = {
//                // NO-OP
//            }
//        )
//    }
//}