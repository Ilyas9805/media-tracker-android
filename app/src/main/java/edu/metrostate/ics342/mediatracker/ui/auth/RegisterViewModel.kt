package edu.metrostate.ics342.mediatracker.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.RegisterResult
import edu.metrostate.ics342.mediatracker.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _displayName     = MutableStateFlow("")
    val displayName              = _displayName.asStateFlow()

    private val _username        = MutableStateFlow("")
    val username                 = _username.asStateFlow()

    private val _email           = MutableStateFlow("")
    val email                    = _email.asStateFlow()

    private val _password        = MutableStateFlow("")
    val password                 = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword          = _confirmPassword.asStateFlow()

    private val _isSuccess       = MutableStateFlow(false)
    val isSuccess                = _isSuccess.asStateFlow()

    private val _errorMessage    = MutableStateFlow<String?>(null)
    val errorMessage             = _errorMessage.asStateFlow()

    fun setDisplayName(value: String)     { _displayName.value     = value }
    fun setUsername(value: String)        { _username.value        = value }
    fun setEmail(value: String)           { _email.value           = value }
    fun setPassword(value: String)        { _password.value        = value }
    fun setConfirmPassword(value: String) { _confirmPassword.value = value }

    fun onSignUpClicked() {
        when {
            _displayName.value.isBlank() || _username.value.isBlank() ||
                    _email.value.isBlank()       || _password.value.isBlank() ||
                    _confirmPassword.value.isBlank() -> {
                _errorMessage.value = "Please fill in all fields."
                return
            }
            _password.value != _confirmPassword.value -> {
                _errorMessage.value = "Passwords do not match."
                return
            }
        }

        viewModelScope.launch {
            _errorMessage.value = null
            val result = userRepository.register(
                email       = _email.value,
                password    = _password.value,
                username    = _username.value,
                displayName = _displayName.value
            )
            when (result) {
                is RegisterResult.Success      -> _isSuccess.value = true
                is RegisterResult.Conflict     -> _errorMessage.value = "That email or username is already taken."
                is RegisterResult.NetworkError -> _errorMessage.value = "Network error. Please check your connection."
                is RegisterResult.UnknownError -> _errorMessage.value = "Something went wrong. Please try again."
            }
        }
    }
}

class RegisterViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(userRepository) as T
    }
}