package edu.metrostate.ics342.mediatracker.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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

    fun setDisplayName(value: String)     { _displayName.value     = value }
    fun setUsername(value: String)        { _username.value        = value }
    fun setEmail(value: String)           { _email.value           = value }
    fun setPassword(value: String)        { _password.value        = value }
    fun setConfirmPassword(value: String) { _confirmPassword.value = value }

    fun onSignUpClicked() {
        viewModelScope.launch {
            try {
                userRepository.createAccount(
                    displayName = _displayName.value,
                    username    = _username.value,
                    email       = _email.value,
                    password    = _password.value
                )
                _isSuccess.value = true
            } catch (e: Exception) {
                // API returned an error — don't crash, just log it for now
                e.printStackTrace()
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