package com.kusitms.connectdog.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor() : ViewModel() {
    private val _isEmailVerified: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEmailVerified: StateFlow<Boolean>
        get() = _isEmailVerified

    private val _email: MutableState<String> = mutableStateOf("")
    val email: String
        get() = _email.value

    private val _certificationNumber: MutableState<String> = mutableStateOf("")
    val certificationNumber: String
        get() = _certificationNumber.value

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updateCertificationNumber(certificationNumber: String) {
        _certificationNumber.value = certificationNumber
    }
}
