package com.kusitms.connectdog.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor() : ViewModel() {
    private val _allChecked = MutableLiveData(false)
    val allChecked: LiveData<Boolean> = _allChecked

    private val _termsChecked = MutableLiveData(false)
    val termsChecked: LiveData<Boolean> = _termsChecked

    private val _privacyChecked = MutableLiveData(false)
    val privacyChecked: LiveData<Boolean> = _privacyChecked

    private val _advertisementChecked = MutableLiveData(false)
    val advertisementChecked: LiveData<Boolean> = _advertisementChecked

    fun updateAllChecked() {
        _allChecked.value = !_allChecked.value!!
        _termsChecked.value = !_allChecked.value!!
        _privacyChecked.value = !_allChecked.value!!
    }

    fun updateTermsChecked() {
        _termsChecked.value = !_termsChecked.value!!
        updateAllCheckedState()
    }

    fun updatePrivacyChecked() {
        _privacyChecked.value = !_privacyChecked.value!!
        updateAllCheckedState()
    }

    fun updateAdvertisementChecked() {
        _advertisementChecked.value = !_advertisementChecked.value!!
    }

    private fun updateAllCheckedState() {
        _allChecked.value = _termsChecked.value == true && _privacyChecked.value == true
    }

    fun resetState() {
        _allChecked.value = false
        _privacyChecked.value = false
        _termsChecked.value = false
    }
}
