package samyups.example.chessclock.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import samyups.example.chessclock.model.MainRepository
import samyups.example.chessclock.ui.MainViewModel

class MainViewModelFactory(private val mainrepository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("Unchecked_Cast")
            return MainViewModel(mainrepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}