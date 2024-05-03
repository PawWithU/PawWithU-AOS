package com.kusitms.connectdog.feature.login.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

): ViewModel() {
    private val _phoneNumber: MutableState<String> = mutableStateOf("")
    val phoneNumber: String
        get() = _phoneNumber.value

    private val _isAvailablePhoneNumber = MutableStateFlow<Boolean?>(null)
    val isAvailablePhoneNumber: Boolean?
        get() = _isAvailablePhoneNumber.value



    fun updatePhoneNumber(value: String) {
        _phoneNumber.value = value
    }
}