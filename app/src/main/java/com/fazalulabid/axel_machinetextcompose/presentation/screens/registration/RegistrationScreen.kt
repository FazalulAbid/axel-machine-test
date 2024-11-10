package com.fazalulabid.axel_machinetextcompose.presentation.screens.registration

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.fazalulabid.axel_machinetextcompose.R
import com.fazalulabid.axel_machinetextcompose.presentation.components.StandardToolbar
import com.fazalulabid.axel_machinetextcompose.presentation.navigation.Screens
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.OutlineColor
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.ProfilePictureSizeLarge
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.SpaceLarge
import com.fazalulabid.axel_machinetextcompose.presentation.ui.theme.SpaceMedium
import com.fazalulabid.axel_machinetextcompose.presentation.util.CropActivityResultContract
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    imageLoader: ImageLoader,
    navigateToLogin: () -> Unit = {},
    onRegister: () -> Unit = {},
    viewModel: RegistrationViewModel = hiltViewModel(),
    editMode: Boolean = false,
) {

    val state = viewModel.formState.value

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.onEvent(RegistrationEvent.DateOfBirthChanged("$dayOfMonth/${month + 1}/$year"))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(RegistrationEvent.CropProfilePicture(it))
    }

    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it == null) return@rememberLauncherForActivityResult
        cropProfilePictureLauncher.launch(it)
    }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(RegistrationEvent.IsFormForEdit(editMode))
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RegistrationUiEvent.RegistrationCompleted -> {
                    Toast.makeText(context, "Registration Complete", Toast.LENGTH_SHORT).show()
                    onRegister()
                }

                is RegistrationUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        StandardToolbar(onNavigateUp = {}, showBackArrow = false, navActions = {
            IconButton(onClick = {
                viewModel.onEvent(RegistrationEvent.Submit)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.save_changes),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }, title = {
            Text(
                text = stringResource(R.string.registration),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = SpaceLarge, vertical = SpaceLarge)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Image(
                painter = rememberImagePainter(
                    data = state.profilePictureUri ?: R.drawable.ic_profile_avatar,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(ProfilePictureSizeLarge)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp, color = OutlineColor, shape = CircleShape
                    )
                    .clickable {
                        profilePictureGalleryLauncher.launch("image/*")
                    },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(SpaceLarge))

            // Username
            TextField(
                value = state.username,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.UsernameChanged(it))
                },
                isError = state.usernameError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Username")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            if (state.usernameError != null) {
                Text(
                    text = state.usernameError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(SpaceMedium))

            // Full Name
            TextField(
                value = state.fullName,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.FullNameChanged(it))
                },
                isError = state.fullNameError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Full Name")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            if (state.fullNameError != null) {
                Text(
                    text = state.fullNameError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(SpaceMedium))

            // Password
            TextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(SpaceMedium))

            // Repeated Password
            TextField(
                value = state.repeatedPassword,
                onValueChange = {
                    viewModel.onEvent(RegistrationEvent.RepeatedPasswordChanged(it))
                },
                isError = state.repeatedPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Repeat Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.repeatedPasswordError != null) {
                Text(
                    text = state.repeatedPasswordError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(SpaceMedium))

            // Date of Birth
            TextField(value = state.dob,
                onValueChange = { },
                enabled = false,
                isError = state.dobError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                placeholder = {
                    Text(text = "Date of Birth")
                })
            if (state.dobError != null) {
                Text(
                    text = state.dobError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(SpaceMedium))

            Text("Already a user? Click here to login", modifier.clickable {
                navigateToLogin()
            })
        }
    }
}