package com.kusitms.connectdog.feature.login.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.SignUpRepository
import com.kusitms.connectdog.core.util.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordSearchViewModel @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {
    private var _password = mutableStateOf("")
    val password: String
        get() = _password.value

    private var _confirmPassword = mutableStateOf("")
    val confirmPassword: String
        get() = _confirmPassword.value

    private val _isValidPassword: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidPassword: StateFlow<Boolean?>
        get() = _isValidPassword

    private val _isValidConfirmPassword: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidConfirmPassword: StateFlow<Boolean?>
        get() = _isValidConfirmPassword

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun checkPasswordValidity(password: String) {
        val pattern1 = "^[a-zA-Z0-9]{10,}$"
        val pattern2 = "^[a-zA-Z0-9!@#$%^&*()_+}{\":?><,./;'\\[\\]]{8,}$"
        _isValidPassword.value = !Regex(pattern1).matches(password) || !Regex(pattern2).matches(password)
    }

    fun checkConfirmPasswordValidity(confirmPassword: String) {
        _isValidConfirmPassword.value = confirmPassword != _password.value
    }

    fun changePassword(userType: UserType) = viewModelScope.launch {
        try {
            when (userType) {
                UserType.INTERMEDIATOR -> repository.changeInterPassword(_password.value)
                else -> repository.changeVolunteerPassword(_password.value)
            }
        } catch (e: Exception) {
        }
    }
}
