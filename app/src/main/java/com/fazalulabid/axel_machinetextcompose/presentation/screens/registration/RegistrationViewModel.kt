package com.fazalulabid.axel_machinetextcompose.presentation.screens.registration

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazalulabid.axel_machinetextcompose.core.util.Resource
import com.fazalulabid.axel_machinetextcompose.domain.model.Account
import com.fazalulabid.axel_machinetextcompose.domain.usecase.GetLoggedInAccountUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.InsertAccountUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.UpdateAccountUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.validations.ValidateUsernameUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.validations.ValidateDOBUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.validations.ValidateEmailUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.validations.ValidateFullNameUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.validations.ValidatePasswordUseCase
import com.fazalulabid.axel_machinetextcompose.domain.usecase.validations.ValidateRepeatedPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val insertAccountUseCase: InsertAccountUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateFullNameUseCase: ValidateFullNameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
    private val validateDOBUseCase: ValidateDOBUseCase,
    private val getLoggedInAccountUseCase: GetLoggedInAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase
) : ViewModel() {

    private val _formState = mutableStateOf(RegistrationState())
    val formState: State<RegistrationState> = _formState

    private val _eventFlow = MutableSharedFlow<RegistrationUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.CropProfilePicture -> {
                _formState.value = _formState.value.copy(
                    profilePictureUri = event.uri
                )
            }

            is RegistrationEvent.DateOfBirthChanged -> {
                _formState.value = _formState.value.copy(
                    dob = event.dob,
                    dobError = null
                )
            }

            is RegistrationEvent.FullNameChanged -> {
                _formState.value = _formState.value.copy(
                    fullName = event.fullName,
                    fullNameError = null
                )
            }

            is RegistrationEvent.PasswordChanged -> {
                _formState.value = _formState.value.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            is RegistrationEvent.RepeatedPasswordChanged -> {
                _formState.value = _formState.value.copy(
                    repeatedPassword = event.repeatedPassword,
                    repeatedPasswordError = null
                )
            }

            is RegistrationEvent.UsernameChanged -> {
                _formState.value = _formState.value.copy(
                    username = event.username,
                    usernameError = null
                )
            }

            RegistrationEvent.Submit -> {
                viewModelScope.launch {
                    if (_formState.value.isFormForEdit) {
                        updateData()
                    } else {
                        saveData()
                    }
                }
            }

            is RegistrationEvent.IsFormForEdit -> {
                _formState.value = _formState.value.copy(
                    isFormForEdit = event.isEdit
                )

                if (event.isEdit) {
                    getUserDataForUpdate()
                }
            }
        }
    }

    private fun getUserDataForUpdate() {
        viewModelScope.launch {
            getLoggedInAccountUseCase(Unit).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _eventFlow.emit(RegistrationUiEvent.ShowToast("Something went wrong!"))
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        result.data?.let { account ->
                            _formState.value = _formState.value.copy(
                                id = account.id,
                                username = account.username,
                                fullName = account.fullName,
                                dob = account.dob,
                                profilePictureUri = account.profilePictureUri?.let { Uri.parse(it) }
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateData() {
        val usernameResult = validateUsernameUseCase.execute(_formState.value.username, true)
        val fullNameResult = validateFullNameUseCase.execute(_formState.value.fullName)
        val dobResult = validateDOBUseCase.execute(_formState.value.dob)

        val hasError = listOf(
            usernameResult,
            fullNameResult,
            dobResult
        ).any { !it.successful }

        if (hasError) {
            _formState.value = _formState.value.copy(
                usernameError = usernameResult.errorMessage,
                fullNameError = fullNameResult.errorMessage,
                dobError = dobResult.errorMessage
            )
            return
        }
        updateAccountUseCase(
            Account(
                id = _formState.value.id,
                username = _formState.value.username,
                fullName = _formState.value.fullName,
                password = _formState.value.password,
                dob = _formState.value.dob,
                profilePictureUri = _formState.value.profilePictureUri?.toString()
            )
        ).collect { result ->
            when (result) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    _eventFlow.emit(RegistrationUiEvent.ProfileUpdated)
                }
            }
        }
    }

    private suspend fun saveData() {
        val usernameResult = validateUsernameUseCase.execute(_formState.value.username)
        val fullNameResult = validateFullNameUseCase.execute(_formState.value.fullName)
        val passwordResult = validatePasswordUseCase.execute(_formState.value.password)
        val repeatedPasswordResult = validateRepeatedPasswordUseCase.execute(
            _formState.value.password,
            _formState.value.repeatedPassword
        )
        val dobResult = validateDOBUseCase.execute(_formState.value.dob)

        val hasError = listOf(
            usernameResult,
            fullNameResult,
            passwordResult,
            repeatedPasswordResult,
            dobResult
        ).any { !it.successful }

        if (hasError) {
            _formState.value = _formState.value.copy(
                usernameError = usernameResult.errorMessage,
                fullNameError = fullNameResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                dobError = dobResult.errorMessage
            )
            return
        }
        insertAccountUseCase(
            InsertAccountUseCase.Params(
                Account(
                    username = _formState.value.username,
                    fullName = _formState.value.fullName,
                    password = _formState.value.password,
                    dob = _formState.value.dob,
                    profilePictureUri = _formState.value.profilePictureUri?.toString()
                )
            )
        ).collect { result ->
            when (result) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    _eventFlow.emit(RegistrationUiEvent.RegistrationCompleted)
                }
            }
        }
    }
}

sealed class RegistrationEvent {
    data class CropProfilePicture(val uri: Uri?) : RegistrationEvent()
    data class UsernameChanged(val username: String) : RegistrationEvent()
    data class FullNameChanged(val fullName: String) : RegistrationEvent()
    data class PasswordChanged(val password: String) : RegistrationEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegistrationEvent()
    data class DateOfBirthChanged(val dob: String) : RegistrationEvent()
    data class IsFormForEdit(val isEdit: Boolean) : RegistrationEvent()
    data object Submit : RegistrationEvent()
}

sealed class RegistrationUiEvent {
    data class ShowToast(val message: String) : RegistrationUiEvent()
    data object RegistrationCompleted : RegistrationUiEvent()
    data object ProfileUpdated : RegistrationUiEvent()
}