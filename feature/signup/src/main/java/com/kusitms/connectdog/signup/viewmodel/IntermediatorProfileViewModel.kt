package com.kusitms.connectdog.signup.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntermediatorProfileViewModel @Inject constructor() : ViewModel() {
    private val _name: MutableState<String> = mutableStateOf("")
    val name: String
        get() = _name.value

    private val _introduce: MutableState<String> = mutableStateOf("")
    val introduce: String
        get() = _introduce.value

    private val _uri: MutableState<Uri?> = mutableStateOf(null)
    val uri: Uri?
        get() = _uri.value

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateIntroduce(introduce: String) {
        _introduce.value = introduce
    }

    fun updateUri(uri: Uri) {
        _uri.value = uri
    }
}
