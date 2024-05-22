package com.kusitms.connectdog.feature.intermediator.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kusitms.connectdog.core.designsystem.component.DayTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateApplicationViewModel @Inject constructor() : ViewModel() {
    private val _uriList = MutableStateFlow<MutableList<Uri>>(mutableListOf())
    val uriList: StateFlow<List<Uri>>
        get() = _uriList

    private val _departure = MutableStateFlow<String>("출발 지역 선택")
    val departure: StateFlow<String>
        get() = _departure

    private val _destination = MutableStateFlow<String>("도착 지역 선택")
    val destination: StateFlow<String>
        get() = _destination

    private val _startDate = MutableStateFlow<LocalDate?>(null)
    val startDate: StateFlow<LocalDate?>
        get() = _startDate

    private val _endDate = MutableStateFlow<LocalDate?>(null)
    val endDate: StateFlow<LocalDate?>
        get() = _endDate

    private val _hour = MutableStateFlow<Int?>(null)
    val hour: StateFlow<Int?>
        get() = _hour

    private val _minute = MutableStateFlow<Int?>(null)
    val minute: StateFlow<Int?>
        get() = _minute

    private val _dayTime = MutableStateFlow<DayTime?>(null)
    val dayTime: StateFlow<DayTime?>
        get() = _dayTime

    private val _isKennel = MutableStateFlow<Boolean?>(null)
    val isKennel: StateFlow<Boolean?>
        get() = _isKennel

    private val _isAdjustableTime = MutableStateFlow(false)
    val isAdjustableTime: StateFlow<Boolean>
        get() = _isAdjustableTime

    private val _isAdjustableSchedule = MutableStateFlow(false)
    val isAdjustableSchedule: StateFlow<Boolean>
        get() = _isAdjustableSchedule

    private val _significant: MutableState<String> = mutableStateOf("")
    val significant: String
        get() = _significant.value

    private val _name: MutableState<String> = mutableStateOf("")
    val name: String
        get() = _name.value

    fun updateUriList(uri: Uri) {
        _uriList.value = _uriList.value.toMutableList().apply { add(uri) }
    }

    fun removeUriList(uri: Uri) {
        _uriList.value = _uriList.value.toMutableList().apply { remove(uri) }
    }

    fun updateStartDate(start: LocalDate) {
        _startDate.value = start
    }

    fun updateEndDate(end: LocalDate) {
        _endDate.value = end
    }

    fun updateDestination(destination: String) {
        _destination.value = destination
    }

    fun updateDeparture(departure: String) {
        _departure.value = departure
    }

    fun updateHour(hour: Int) {
        _hour.value = hour
    }

    fun updateMinute(minute: Int) {
        _minute.value = minute
    }

    fun updateDayTime(dayTime: DayTime) {
        _dayTime.value = dayTime
    }

    fun updateIsKennel(value: Boolean) {
        _isKennel.value = value
    }

    fun updateIsAdjustableTime(value: Boolean) {
        _isAdjustableTime.value = value
    }

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateSignificant(description: String) {
        _significant.value = description
    }

    fun updateIsAdjustableSchedule(value: Boolean) {
        _isAdjustableSchedule.value = value
    }
}
