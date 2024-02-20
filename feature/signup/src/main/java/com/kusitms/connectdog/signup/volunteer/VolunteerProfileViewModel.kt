package com.kusitms.connectdog.signup.volunteer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kusitms.connectdog.core.util.getProfileImageId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VolunteerProfileViewModel @Inject constructor() : ViewModel() {
    private val _selectedImageIndex = MutableStateFlow(-1)
    val selectedImageIndex: StateFlow<Int>
        get() = _selectedImageIndex

    private val _isNicknameAvailable: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isAvailableNickname: StateFlow<Boolean?>
        get() = _isNicknameAvailable

    private val _nickname: MutableState<String> = mutableStateOf("")
    val nickname: String
        get() = _nickname.value

    fun updateNicknameAvailability(nickname: String) {
        _isNicknameAvailable.value = nickname == "ssaaw"
    }

    fun updateProfileImageIndex(imageIndex: Int) {
        _selectedImageIndex.value = imageIndex
    }

    fun updateNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun getProfileImage(): Int {
        return if (_selectedImageIndex.value == -1) {
            com.kusitms.connectdog.core.designsystem.R.drawable.ic_circle
        } else {
            getProfileImageId(_selectedImageIndex.value)
        }
    }
}
