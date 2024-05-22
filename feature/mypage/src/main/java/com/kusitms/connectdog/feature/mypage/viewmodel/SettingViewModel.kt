package com.kusitms.connectdog.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.DataStoreRepository
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import com.kusitms.connectdog.core.util.AppMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MyPageViewModel"

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val myPageRepository: MyPageRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {
    // TODO AccessToken을 빈 문자열로 초기화하지 않고 삭제
    fun initLogout() = viewModelScope.launch {
        try {
            dataStoreRepository.saveAppMode(AppMode.LOGIN)
            dataStoreRepository.saveAccessToken("")
            loginRepository.logout()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            try {
                myPageRepository.deleteAccount()
                dataStoreRepository.saveAccessToken("")
            } catch (e: Exception) {
                Log.d("tesaq", e.message.toString())
            }
        }
    }
}
