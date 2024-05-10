package com.kusitms.connectdog.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MAIN_VIEWMODEL"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    fun updateFcmToken(token: String) = viewModelScope.launch {
        dataStoreRepository.saveFcmToken(token)
    }
}
