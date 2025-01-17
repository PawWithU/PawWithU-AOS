package com.kusitms.connectdog.feature.intermediator.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.FcmTokenRequestBody
import com.kusitms.connectdog.core.data.repository.DataStoreRepository
import com.kusitms.connectdog.core.data.repository.InterManagementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterHomeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val repository: InterManagementRepository
) : ViewModel() {
    init {
        postFcmToken()
    }
    private val _profileImage = MutableStateFlow<String>("")
    val profileImage: StateFlow<String>
        get() = _profileImage

    private val _completedCount = MutableStateFlow<Int?>(null)
    val completedCount: StateFlow<Int?>
        get() = _completedCount

    private val _progressingCount = MutableStateFlow<Int?>(null)
    val progressingCount: StateFlow<Int?>
        get() = _progressingCount

    private val _waitingCount = MutableStateFlow<Int?>(null)
    val waitingCount: StateFlow<Int?>
        get() = _waitingCount

    private val _recruitingCount = MutableStateFlow<Int?>(null)
    val recruitingCount: StateFlow<Int?>
        get() = _recruitingCount

    private val _intro = MutableStateFlow<String>("")
    val intro: StateFlow<String>
        get() = _intro

    private val _intermediaryName = MutableStateFlow<String>("")
    val intermediaryName: StateFlow<String>
        get() = _intermediaryName

    fun fetchIntermediatorInfo() {
        viewModelScope.launch {
            try {
                val response = repository.getIntermediatorProfileInfo()
                _profileImage.value = response.profileImage.toString()
                _intermediaryName.value = response.intermediaryName
                _completedCount.value = response.completedCount.toInt()
                _progressingCount.value = response.progressingCount.toInt()
                _waitingCount.value = response.waitingCount.toInt()
                _recruitingCount.value = response.recruitingCount.toInt()
                _intro.value = response.intro
            } catch (e: Exception) {
                Log.d("asdf", e.message.toString())
            }
        }
    }

    private fun postFcmToken() = viewModelScope.launch {
        val token = dataStoreRepository.fcmTokenFlow.first()
        try {
            token?.let { repository.postFcmToken(FcmTokenRequestBody(it)) }
        } catch (e: Exception) {
            Log.d("fcm post", e.message.toString())
        }
    }
}
