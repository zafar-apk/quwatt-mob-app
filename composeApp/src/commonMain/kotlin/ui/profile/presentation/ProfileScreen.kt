package ui.profile.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.ImagePickerLauncher
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.domain.util.stringResource
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.core.getOrNull
import io.kamel.image.asyncPainterResource
import profile.domain.User
import profile.presentation.ProfileScreenEvent
import profile.presentation.ProfileScreenState
import tj.ham_safar.app.android.core.presentation.components.Loader
import tj.ham_safar.app.android.core.presentation.components.TopBar
import tj.ham_safar.app.android.register.user.presentation.user.components.LineTextField
import tj.yakroh.yakrohapp.SharedRes
import ui.core.presentation.components.ErrorView
import ui.core.presentation.components.LoginView
import ui.register.user.presentation.user.DateOfBirthField
import ui.theme.LightGray
import ui.trips.components.ActionItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    state: ProfileScreenState,
    onEvent: (ProfileScreenEvent) -> Unit
) {
    LaunchedEffect(key1 = state) {
        with(state) {
            when {
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
                text = stringResource(SharedRes.strings.profile),
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
                        actionName = stringResource(SharedRes.strings.transport),
                        actionIcon = painterResource(SharedRes.images.ic_arrow_thin),
                        actionIconContentDescription = stringResource(SharedRes.strings.arrow_right),
                        onAction = { onEvent(ProfileScreenEvent.OpenTransport) }
                    )
                }

                item {
                    if (state.user.transport != null) ActionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        actionName = stringResource(SharedRes.strings.driver_licence),
                        actionIcon = painterResource(SharedRes.images.ic_arrow_thin),
                        actionIconContentDescription = stringResource(SharedRes.strings.arrow_right),
                        onAction = { onEvent(ProfileScreenEvent.OpenLicence) }
                    )
                }

                if (state.user.isDriver) item {
                    ActionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        actionName = stringResource(SharedRes.strings.my_trips),
                        actionIcon = painterResource(SharedRes.images.ic_list_item),
                        actionIconContentDescription = stringResource(SharedRes.strings.my_trips),
                        onAction = { onEvent(ProfileScreenEvent.OpenMyTrips) }
                    )
                }

                item {
                    ActionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        actionName = stringResource(SharedRes.strings.my_requests),
                        actionIcon = painterResource(SharedRes.images.ic_people),
                        actionIconContentDescription = stringResource(SharedRes.strings.my_requests),
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
                                text = stringResource(SharedRes.strings.logout),
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

@Composable
private fun UserInfo(
    user: User?,
    onEvent: (ProfileScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var preview: ByteArray? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    val launcher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        resizeOptions = ResizeOptions(width = 800, height = 800),
        onResult = { byteArrays ->
            byteArrays.firstOrNull()
                ?.let(ProfileScreenEvent::ChangeProfilePhoto)
                ?.let(onEvent)
        }
    )

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.size(12.dp))

            Box(modifier = Modifier.align(CenterHorizontally)) {
                when {
                    preview != null -> {
                        Image(
                            modifier = getUserImageModifier(launcher),
                            contentDescription = stringResource(SharedRes.strings.user_photo),
                            bitmap = (preview ?: byteArrayOf()).toImageBitmap()
                        )
                    }

                    else -> {
                        Image(
                            modifier = getUserImageModifier(launcher),
                            contentDescription = stringResource(SharedRes.strings.user_photo),
                            painter = user?.photo?.let { asyncPainterResource(it) }?.getOrNull()
                                ?: painterResource(SharedRes.images.ic_user)
                        )
                    }
                }

                Image(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .clickable {
                            launcher.launch()
                        },
                    painter = painterResource(SharedRes.images.ic_edit),
                    contentDescription = stringResource(SharedRes.strings.edit)
                )
            }

            Spacer(modifier = Modifier.size(35.dp))

            LineTextField(
                modifier = Modifier.padding(horizontal = 27.dp),
                label = stringResource(SharedRes.strings.name_label),
                value = user?.name.orEmpty(),
                onValueChange = { /* no-op */ },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(),
                isEditable = false
            )

            Spacer(modifier = Modifier.size(14.dp))

            LineTextField(
                modifier = Modifier.padding(horizontal = 27.dp),
                label = stringResource(SharedRes.strings.sur_name_label),
                value = user?.surname.orEmpty(),
                onValueChange = { /* no-op */ },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(),
                isEditable = false
            )

            Spacer(modifier = Modifier.size(14.dp))

            LineTextField(
                modifier = Modifier.padding(horizontal = 27.dp),
                label = stringResource(SharedRes.strings.patronymic_label),
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

private fun getUserImageModifier(launcher: ImagePickerLauncher) = Modifier
    .size(150.dp)
    .clip(CircleShape)
    .background(LightGray)

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