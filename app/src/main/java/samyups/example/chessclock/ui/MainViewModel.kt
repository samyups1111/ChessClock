package samyups.example.chessclock.ui

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import samyups.example.chessclock.model.TimeSetting
import samyups.example.chessclock.model.MainRepository

class MainViewModel(private val repository: MainRepository): ViewModel() {

    val timeSettingsListLiveData: LiveData<List<TimeSetting>> = repository.timeSettingsListFlow.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

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