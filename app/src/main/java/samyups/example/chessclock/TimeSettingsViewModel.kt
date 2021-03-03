package samyups.example.chessclock

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TimeSettingsViewModel(private val repository: TimeSettingsRepository): ViewModel() {
    // Using LiveData and caching what allTimes returns has several benefits:
    // -We can put an observer on the data (instead of polling for changes) and only update the
    // UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    val allTimes: LiveData<List<TimeSettings>> = repository.allTimes.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun insert(timeSettings: TimeSettings) = viewModelScope.launch {
        repository.insert(timeSettings)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    class TimeSettingsFactory(private val repository: TimeSettingsRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TimeSettingsViewModel::class.java)) {
                @Suppress("Unchecked_Cast")
                return TimeSettingsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}