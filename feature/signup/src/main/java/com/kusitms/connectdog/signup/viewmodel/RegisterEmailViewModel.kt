package com.kusitms.connectdog.signup.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationBody
import com.kusitms.connectdog.core.data.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {
    private val _isEmailVerified: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isEmailVerified: StateFlow<Boolean?>
        get() = _isEmailVerified

    private val _isEmailDuplicated: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isEmailDuplicated: StateFlow<Boolean?>
        get() = _isEmailDuplicated

    private val _isValidEmail: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidEmail: StateFlow<Boolean?>
        get() = _isValidEmail

    private val _email: MutableState<String> = mutableStateOf("")
    val email: String
        get() = _email.value

    private var postNumber: String = ""

    private val _certificationNumber: MutableState<String> = mutableStateOf("")
    val certificationNumber: String
        get() = _certificationNumber.value

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

    fun postEmail(context: Context) = viewModelScope.launch {
        if (isValidEmail.value == true) {
            Toast.makeText(context, "유효한 이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return@launch
        }

        val body = EmailCertificationBody(_email.value)
        try {
            val response = signUpRepository.postEmail(body)
            Toast.makeText(context, "이메일을 전송했습니다", Toast.LENGTH_SHORT).show()
            _isEmailDuplicated.value = false
            postNumber = response.authCode
        } catch (e: Exception) {
            _isEmailDuplicated.value = true
            Toast.makeText(context, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkCertificationNumber(context: Context) {
        if (_certificationNumber.value.isEmpty()) {
            Toast.makeText(context, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        } else {
            _isEmailVerified.value = _certificationNumber.value == postNumber
            if (_isEmailVerified.value == true) {
                Toast.makeText(context, "인증이 완료되었습니다", Toast.LENGTH_SHORT).show()
            } else if (_isEmailVerified.value == true) {
                Toast.makeText(context, "인증에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
