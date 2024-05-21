package com.kusitms.connectdog.signup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberBody
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.NormalVolunteerSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.SocialVolunteerSignUpBody
import com.kusitms.connectdog.core.data.repository.DataStoreRepository
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.kusitms.connectdog.core.data.repository.SignUpRepository
import com.kusitms.connectdog.core.util.AppMode
import com.kusitms.connectdog.core.util.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SignUpViewModel"

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signupRepository: SignUpRepository,
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _email = MutableStateFlow<String>("")
    private val _password = MutableStateFlow<String>("")
    private val _nickname = MutableStateFlow<String>("")
    private val _profileImageId = MutableStateFlow<Int?>(null)
    private val _phoneNumber = MutableStateFlow<String>("")
    private val _contact = MutableStateFlow<String>("")
    private val _intro = MutableStateFlow<String>("")
    private val _url = MutableStateFlow<String>("")
    private val _name = MutableStateFlow<String>("")

    val isDuplicatePhoneNumber = MutableSharedFlow<Boolean>()

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

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateIntro(intro: String) {
        _intro.value = intro
    }

    fun updateUrl(url: String) {
        _url.value = url
    }

    fun updateContact(contact: String) {
        _contact.value = contact
    }

    fun updatePhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun postNormalVolunteerSignUp() {
        viewModelScope.launch {
            val body = NormalVolunteerSignUpBody(
                name = _name.value,
                phone = _phoneNumber.value,
                email = _email.value,
                nickname = _nickname.value,
                password = _password.value,
                profileImageNum = _profileImageId.value!!
            )
            try {
                signupRepository.postNormalVolunteerSignUp(body)
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun setAutoLogin(appMode: AppMode, userType: UserType) = viewModelScope.launch {
        val body = NormalLoginBody(
            email = _email.value,
            password = _password.value
        )
        try {
            when (userType) {
                UserType.SOCIAL_VOLUNTEER -> {}
                UserType.NORMAL_VOLUNTEER -> {
                    val response = loginRepository.postLoginData(body)
                    dataStoreRepository.apply {
                        saveAppMode(appMode)
                        saveAccessToken(response.accessToken)
                        saveRefreshToken(response.refreshToken)
                    }
                }
                UserType.INTERMEDIATOR -> {
                    val response = loginRepository.postIntermediatorLoginData(body)
                    dataStoreRepository.apply {
                        saveAppMode(appMode)
                        saveAccessToken(response.accessToken)
                        saveRefreshToken(response.refreshToken)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("asdftt", e.toString())
        }
    }

    fun postSocialVolunteerSignUp() {
        val body = SocialVolunteerSignUpBody(
            nickname = _nickname.value!!,
            profileImageNum = _profileImageId.value!!
        )
        viewModelScope.launch {
            try {
                signupRepository.postSocialVolunteerSignUp(body)
            } catch (e: Exception) {
                Log.d("tesqz", e.message.toString())
            }
        }
    }

    fun postIntermediatorSignUp() {
        val body = IntermediatorSignUpBody(
            email = _email.value,
            password = _password.value,
            realName = _name.value,
            isOptionAgr = true,
            intro = _intro.value,
            url = _url.value,
            contact = _contact.value,
            phone = _phoneNumber.value,
            name = _nickname.value
        )
        viewModelScope.launch {
            try {
                signupRepository.postIntermediatorSignUp(body)
            } catch (e: Exception) {
                Log.d("SignUpViewModel", e.message.toString())
            }
        }
    }

    fun checkIsDuplicatePhoneNumber(userType: UserType, phoneNumber: String) = viewModelScope.launch {
        val body = IsDuplicatePhoneNumberBody(phone = phoneNumber)
        when (userType) {
            UserType.INTERMEDIATOR -> {
                val response = signupRepository.getInterMediatorPhoneNumberDuplicated(body)
                isDuplicatePhoneNumber.emit(response.isDuplicated)
            }
            else -> {
                val response = signupRepository.getVolunteerPhoneNumberDuplicated(body)
                isDuplicatePhoneNumber.emit(response.isDuplicated)
            }
        }
    }
}
