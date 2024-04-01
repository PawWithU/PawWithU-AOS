package com.kusitms.connectdog.signup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.volunteer.NormalVolunteerSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.SocialVolunteerSignUpBody
import com.kusitms.connectdog.core.data.repository.DataStoreRepository
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.kusitms.connectdog.core.data.repository.SignUpRepository
import com.kusitms.connectdog.core.util.AppMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signupRepository: SignUpRepository,
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> = _email

    private val _password = MutableStateFlow<String?>(null)
    val password: StateFlow<String?> = _password

    private val _nickname = MutableStateFlow<String?>(null)
    val nickname: StateFlow<String?> = _nickname

    private val _profileImageId = MutableStateFlow<Int?>(null)
    val profileImageId: StateFlow<Int?> = _profileImageId

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun updateProfileImageId(profileImageId: Int) {
        _profileImageId.value = profileImageId
    }

    fun postNormalVolunteerSignUp() {
        val body = NormalVolunteerSignUpBody(
            email = _email.value!!,
            nickname = _nickname.value!!,
            password = _password.value!!,
            profileImageNum = _profileImageId.value!!
        )
        val login = NormalLoginBody(
            email = _email.value!!,
            password = _password.value!!
        )
        viewModelScope.launch {
            try {
                signupRepository.postNormalVolunteerSignUp(body)
                val response = loginRepository.postLoginData(login)
                dataStoreRepository.saveAppMode(AppMode.VOLUNTEER)
                dataStoreRepository.saveAccessToken(response.accessToken)
                dataStoreRepository.saveRefreshToken(response.refreshToken)
            } catch (e: Exception) {
                Log.d("tesaq", e.message.toString())
            }
        }
    }

    fun postSocialVolunteerSignUp() {
        val body = SocialVolunteerSignUpBody(
            nickname = _nickname.value!!,
            profileImageNum = _profileImageId.value!!
        )
        viewModelScope.launch {
            try {
                val response = signupRepository.postSocialVolunteerSignUp(body)
            } catch (e: Exception) {
                Log.d("tesqz", e.message.toString())
            }
        }
    }
}
