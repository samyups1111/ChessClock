package samyups.example.chessclock.ui

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import samyups.example.chessclock.model.TimeSetting
import samyups.example.chessclock.model.MainRepository
import samyups.example.chessclock.utils.milliToMinSec
import samyups.example.chessclock.utils.timeToMilliSec

class MainViewModel(private val repository: MainRepository): ViewModel() {

    private val TAG = "MainViewModel"

    private var _hoursA : Long = 0
    fun hoursA() = _hoursA
    private var _hoursB : Long = 0
    fun hoursB() = _hoursB

    private var _minutesA: Long = 0
    fun minutesA() = _minutesA
    private var _minutesB: Long = 0
    fun minutesB() = _minutesB

    private var _secondsA : Long = 0
    fun secondsA() = _secondsA
    private var _secondsB: Long = 0
    fun secondsB() = _secondsB

    private var _displayTimeA = MutableLiveData<String>()
    val displayTimeA : LiveData<String> = _displayTimeA
    private var _displayTimeB = MutableLiveData<String>()
    val displayTimeB : LiveData<String> = _displayTimeB

    val timeSettingsListLiveData: LiveData<List<TimeSetting>> = repository.timeSettingsListFlow.asLiveData()

    fun setTimeA(hours: Long, mins: Long, secs : Long) {
        Log.d(TAG, "setTime activated")
        _hoursA = hours
        _minutesA = mins
        _secondsA = secs
        val timeInMilli = timeToMilliSec(hours, mins, secs)
        Log.d(TAG, "MVM: timInMilli = $timeInMilli")
        _displayTimeA.value = milliToMinSec(timeInMilli)
    }

    fun setTimeB(hours: Long, mins: Long, secs : Long) {
        Log.d(TAG, "setTime activated")
        _hoursB = hours
        _minutesB = mins
        _secondsB = secs
        val timeInMilli = timeToMilliSec(hours, mins, secs)
        Log.d(TAG, "MVM: timInMilli = $timeInMilli")
        _displayTimeB.value = milliToMinSec(timeInMilli)
    }

    fun resetTimes() {
        setTimeA(0, 0, 0)
        setTimeB(0, 0, 0)
    }

    fun insert(timeSetting: TimeSetting) = viewModelScope.launch {
        repository.insert(timeSetting)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun delete(timeSetting: TimeSetting) = viewModelScope.launch {
        repository.delete(timeSetting)
    }
}