package samyups.example.chessclock

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MySavedTimesViewModel(private val repository: MySavedTimesRepository): ViewModel() {
    // Using LiveData and caching what allTimes returns has several benefits:
    // -We can put an observer on the data (instead of polling for changes) and only update the
    // UI when the data atually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    val allTimes: LiveData<List<MySavedTimes>> = repository.allTimes.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun insert(mySavedTimes: MySavedTimes) = viewModelScope.launch {
        repository.insert(mySavedTimes)
    }

    class MySavedTimesFactory(private val repository: MySavedTimesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MySavedTimesViewModel::class.java)) {
                @Suppress("Unchecked_Cast")
                return MySavedTimesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    @Override
    override fun onCleared() {
        super.onCleared()
    }


}