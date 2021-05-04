package samyups.example.chessclock.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import samyups.example.chessclock.databinding.SaveTimeDialogBinding
import samyups.example.chessclock.model.TimeSetting
import samyups.example.chessclock.utils.timeToMilliSec

class SaveTimeDialog(private val mainViewModel: MainViewModel): DialogFragment() {

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
        initSetDelayAEditText()
        initSetDelayBEditText()
        initSetIncrementAEditText()
        initSetIncrementBEditText()
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
            //initDelayButtonA()
            //initDelayButtonB()
            saveTimeSetting()
            dismiss()
        }
    }

    private fun initDelayButtonA(): Boolean {
        return binding?.delayA?.isChecked!!
    }

    private fun initSetDelayAEditText() {

        binding?.delayA?.setOnClickListener {
            Log.d(TAG, "delayASetOnClickListener")
            if (binding?.delayA?.isChecked == true) {
                Log.d(TAG, "delayA is checked = ${binding?.delayA?.isChecked }")
                binding?.setDelayAEdittext?.isVisible = true
                Log.d(TAG,"setDelayEditTextVisibility = ${binding?.setDelayAEdittext?.isVisible}")
                binding?.setDelayAEdittext?.isShown
                Log.d(TAG, "delayAEdtiText is shown = ${binding?.setDelayAEdittext?.isShown}")
            } else {
                binding?.setDelayAEdittext?.isVisible = false
            }
        }
    }

    private fun initSetDelayBEditText() {

        binding?.delayB?.setOnClickListener {
            Log.d(TAG, "delayASetOnClickListener")
            if (binding?.delayB?.isChecked == true) {
                binding?.setDelayBEdittext?.isVisible = true
                binding?.setDelayBEdittext?.isShown
            } else {
                binding?.setDelayBEdittext?.isVisible = false
            }
        }
    }

    private fun initSetIncrementAEditText() {

        binding?.incrementA?.setOnClickListener {
            if (binding?.incrementA?.isChecked == true) {
                binding?.setIncrementAEdittext?.isVisible = true
                binding?.setIncrementAEdittext?.isShown
            } else {
                binding?.setIncrementAEdittext?.isVisible = false
            }
        }
    }

    private fun initSetIncrementBEditText() {

        binding?.incrementB?.setOnClickListener {
            if (binding?.incrementB?.isChecked == true) {
                binding?.setIncrementBEdittext?.isVisible = true
                binding?.setIncrementBEdittext?.isShown
            } else {
                binding?.setIncrementBEdittext?.isVisible = false
            }
        }
    }

    private fun setDelayTimeA(): String {
        return if (initDelayButtonA()) {
            (binding?.setDelayAEdittext?.text.toString().toLong() * 1000).toString()
        }
        else "5000"

    }

    private fun initDelayButtonB(): Boolean {
        return binding?.delayB?.isChecked!!
    }

    private fun setDelayTimeB(): String {
        return if (initDelayButtonB()) {
            (binding?.setDelayBEdittext?.text.toString().toLong() * 1000).toString()
        }
        else "5000"
    }

    private fun initIncrementButtonA(): Boolean {
        return binding?.incrementA?.isChecked!!
    }

    private fun setIncrementTimeA(): String {
        return if (initIncrementButtonA()) {
            (binding?.setIncrementAEdittext?.text.toString().toLong() * 1000).toString()
        }
        else "5000"
    }

    private fun initIncrementButtonB(): Boolean {
        return binding?.incrementB?.isChecked!!
    }

    private fun setIncrementTimeB(): String {
        return if (initIncrementButtonB()) {
            (binding?.setIncrementBEdittext?.text.toString().toLong() * 1000).toString()
        }
        else "5000"
    }

    private fun saveTimeSetting() {
        val hoursA: Long = binding?.viewModel?.hoursA()?: 0L
        val minutesA: Long = binding?.viewModel?.minutesA()?: 0L
        val secondsA: Long = binding?.viewModel?.secondsA()?: 0L
        val timerALong = timeToMilliSec(hoursA, minutesA, secondsA).toString()

        val hoursB : Long = binding?.viewModel?.hoursB()?: 0L
        val minutesB: Long = binding?.viewModel?.minutesB()?: 0L
        val secondsB: Long = binding?.viewModel?.secondsB()?: 0L
        val timerBLong = timeToMilliSec(hoursB, minutesB, secondsB).toString()

        val newTime = TimeSetting(
                0,
                timerALong,
                timerBLong,
                initDelayButtonA(),
                setDelayTimeA(),
                initDelayButtonB(),
                setDelayTimeB(),
                initIncrementButtonA(),
                setIncrementTimeA(),
                initIncrementButtonB(),
                setIncrementTimeB()
        )
        binding?.viewModel?.insert(newTime)
        Log.d(TAG, "saving... newTime.setDelayTimeA = ${setDelayTimeA()}")
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