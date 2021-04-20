package samyups.example.chessclock.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import samyups.example.chessclock.databinding.SetTimeBDialogBinding

class SetTimeBDialog(private val mainViewModel: MainViewModel): DialogFragment() {

    private var minutes = 0L
    private var seconds = 0L
    private val TAG = "SetTimeBDialog"
    private var binding: SetTimeBDialogBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mainDialogView = SetTimeBDialogBinding.inflate(inflater, container, false)
        binding = mainDialogView
        return mainDialogView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        initTimer()
    }

    private fun initTimer() {
        val hoursList = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

        val minutesList = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
            "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
            "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
            "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
            "57", "58", "59")

        val secondsList = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
            "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
            "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
            "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
            "57", "58", "59")
        binding?.selectHour?.minValue = 1
        binding?.selectMinutes?.minValue = 1
        binding?.selectSeconds?.minValue = 1

        binding?.selectHour?.maxValue = hoursList.size
        binding?.selectMinutes?.maxValue = minutesList.size
        binding?.selectSeconds?.maxValue = secondsList.size


        binding?.selectHour?.displayedValues = hoursList
        binding?.selectMinutes?.displayedValues = minutesList
        binding?.selectSeconds?.displayedValues = secondsList

        binding?.submitButton?.setOnClickListener {
            minutes = minutesList[binding?.selectMinutes?.value!! - 1].toLong()
            seconds = secondsList[binding?.selectSeconds?.value!! - 1].toLong()
            binding?.viewModel?.setTimeB(minutes, seconds)
            Log.d(TAG, "minutes = $minutes \n seconds = $seconds")
            Log.d(TAG, "viewModel.displaytime = ${binding?.viewModel?.displayTimeB}")
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}