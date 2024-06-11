package com.kusitms.connectdog.feature.login.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.kusitms.connectdog.core.util.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    private val _phoneNumber: MutableState<String> = mutableStateOf("")
    val phoneNumber: String
        get() = _phoneNumber.value

    private val _certificationNumber: MutableState<String> = mutableStateOf("")
    val certificationNumber: String
        get() = _certificationNumber.value

    private val _isSendNumber: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSendNumber: StateFlow<Boolean> = _isSendNumber

    private val _isCertified: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCertified: StateFlow<Boolean> = _isCertified

    private val _findEmail = MutableStateFlow<String?>(null)
    val findEmail: StateFlow<String?>
        get() = _findEmail

    fun updateCertificationNumber(value: String) {
        _certificationNumber.value = value
    }

    fun updatePhoneNumber(value: String) {
        _phoneNumber.value = value
    }

    fun updateIsSendNumber(value: Boolean) {
        _isSendNumber.value = value
    }

    fun updateIsCertified(isCertified: Boolean) {
        _isCertified.value = isCertified
    }

    fun test(userType: UserType, context: Context) = viewModelScope.launch {
        try {
            when (userType) {
                UserType.INTERMEDIATOR -> {
                    val response = repository.interEmailSearch(_phoneNumber.value)
                    _findEmail.value = response.email
                }

                UserType.NORMAL_VOLUNTEER -> {
                    val response = repository.volunteerEmailSearch(_phoneNumber.value)
                    _findEmail.value = response.email
                }

                else -> {}
            }
        } catch (e: Exception) {
            when (userType) {
                UserType.INTERMEDIATOR -> Toast.makeText(context, "해당 해당 이동봉사 모집자를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, "해당 이동봉사자를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
