package samyups.example.chessclock.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import samyups.example.chessclock.databinding.SaveTimeDialogBinding
import samyups.example.chessclock.model.TimeSetting
import samyups.example.chessclock.utils.timeToMilliSec

class SaveTimeDialog(private val mainViewModel: MainViewModel): DialogFragment() {

    private lateinit var timerAButton : Button
    private lateinit var timerBButton: Button
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var setTimeADialog: SetTimeADialog
    private lateinit var setTimeBDialog: SetTimeBDialog
    private var binding : SaveTimeDialogBinding? = null
    private val TAG = "SaveTimeDialog"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = SaveTimeDialogBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBinding()
        initUI()
    }

    private fun initBinding() {
        binding?.apply {
            viewModel = mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun initUI() {
        initTimerAButton()
        initTimerBButton()
        initCancelButton()
        initSaveButton()
    }

    private fun initTimerAButton() {
        binding?.timerAButton?.setOnClickListener {
            showSetTimeADialog()
        }
    }

    private fun initTimerBButton() {
        binding?.timerBButton?.setOnClickListener {
            showSetTimeBDialog()
        }
    }

    private fun initCancelButton() {
        binding?.saveTimeCancelButton?.setOnClickListener {
            binding?.viewModel?.resetTimes()
            dismiss()
        }
    }

    private fun initSaveButton() {
        binding?.saveTimeSaveButton?.setOnClickListener {
            saveTimeSetting()
            dismiss()
        }
    }

    private fun saveTimeSetting() {
        val hoursA: Long = binding?.viewModel?.hoursA()?: 0L
        val minutesA: Long = binding?.viewModel?.minutesA()?: 0L
        val secondsA: Long = binding?.viewModel?.secondsA()?: 0L
        val timerALong = timeToMilliSec(hoursA, minutesA, secondsA).toString()
        val hoursB : Long = binding?.viewModel?.hoursB()?: 0L
        val minutesB: Long = binding?.viewModel?.minutesB()?: 0L
        val secondsB: Long = binding?.viewModel?.secondsB()?: 0L
        val timerBLong = timeToMilliSec(hoursA, minutesB, secondsB).toString()

        val newTime = TimeSetting(0, timerALong, timerBLong)
        Log.d(TAG, "minutesA = $minutesA, secondsA = $secondsA, minutesB = $minutesB, secondsB = $secondsB")
        Log.d(TAG, "timeALong = $timerALong, timeBLong = $timerBLong")
        binding?.viewModel?.insert(newTime)
    }

    private fun showSetTimeADialog() {
        setTimeADialog = SetTimeADialog(mainViewModel)
        setTimeADialog.show(fragmentManager!!, "setTimeADialog")
    }

    private fun showSetTimeBDialog() {
        setTimeBDialog = SetTimeBDialog(mainViewModel)
        setTimeBDialog.show(fragmentManager!!, "setTimeBDialog")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        binding?.viewModel?.resetTimes()
    }

    init {
        binding?.viewModel?.resetTimes()
    }
}