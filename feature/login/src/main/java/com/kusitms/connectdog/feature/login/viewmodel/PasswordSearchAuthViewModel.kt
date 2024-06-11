package com.kusitms.connectdog.feature.login.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.DataStoreRepository
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.kusitms.connectdog.core.util.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordSearchAuthViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private var postNumber: String = ""

    private val _isEmailVerified: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isEmailVerified: StateFlow<Boolean?>
        get() = _isEmailVerified

    private val _isEmailError: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isEmailError: StateFlow<Boolean?>
        get() = _isEmailError

    private val _isValidEmail: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidEmail: StateFlow<Boolean?>
        get() = _isValidEmail

    private val _email: MutableState<String> = mutableStateOf("")
    val email: String
        get() = _email.value

    private val _certificationNumber: MutableState<String> = mutableStateOf("")
    val certificationNumber: String
        get() = _certificationNumber.value

    private val accessToken = MutableStateFlow<String?>(null)

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updateCertificationNumber(certificationNumber: String) {
        _certificationNumber.value = certificationNumber
    }

    fun updateEmailValidity() {
        _isValidEmail.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
    }

    fun updateEmailVerify(value: Boolean) {
        _isEmailVerified.value = value
    }

    fun postEmail(context: Context, userType: UserType) = viewModelScope.launch {
        if (_email.value.isEmpty()) {
            Toast.makeText(context, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return@launch
        }
        if (isValidEmail.value == true) {
            Toast.makeText(context, "유효한 이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return@launch
        }

        try {
            val response = when (userType) {
                UserType.INTERMEDIATOR -> repository.interPasswordSearchAuth(_email.value)
                else -> repository.volunteerPasswordSearchAuth(_email.value)
            }
            Toast.makeText(context, "이메일을 전송했습니다", Toast.LENGTH_SHORT).show()
            _isEmailError.value = false
            postNumber = response.authCode
            accessToken.value = response.accessToken
        } catch (e: Exception) {
            val text = when (userType) {
                UserType.INTERMEDIATOR -> "해당 이동봉사 모집자를 찾을 수 없습니다."
                else -> "해당 봉사자를 찾을 수 없습니다."
            }
            _isEmailError.value = true
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    fun checkCertificationNumber(context: Context) = viewModelScope.launch {
        if (_certificationNumber.value.isEmpty()) {
            Toast.makeText(context, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        } else {
            _isEmailVerified.value = _certificationNumber.value == postNumber
            if (_isEmailVerified.value == true) {
                accessToken.value?.let { dataStoreRepository.saveAccessToken(it) }
                Toast.makeText(context, "인증이 완료되었습니다", Toast.LENGTH_SHORT).show()
            } else if (_isEmailVerified.value == false) {
                Toast.makeText(context, "인증에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
