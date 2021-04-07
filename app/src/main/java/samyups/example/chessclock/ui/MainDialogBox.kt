package samyups.example.chessclock.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_box_main.*
import kotlinx.android.synthetic.main.dialog_box_main.view.*
import samyups.example.chessclock.R
import samyups.example.chessclock.model.TimeSetting

class MainDialogBox(private val mainViewModel: MainViewModel): DialogFragment() {

    private var minutesA = 0L
    private var secondsA = 0L
    private var minutesB = 0L
    private var secondsB = 0L


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val mainDialogView: View = inflater.inflate(R.layout.dialog_box_main, container, false)
        iniUI(mainDialogView)
        return mainDialogView
    }

    private fun iniUI(mainDialogView: View) {
        mainDialogView.cancel_button.setOnClickListener {
            dismiss()
        }

        mainDialogView.submit_button.setOnClickListener {
            minutesA = 60_000L * (add_minutes_a_textview.text.toString().toLongOrNull() ?: 0L)
            secondsA = 1_000L * (add_seconds_a_textview.text.toString().toLongOrNull() ?: 0L)
            minutesB = 60_000L * (add_minutes_b_textview.text.toString().toLongOrNull() ?: 0L)
            secondsB = 1_000 * (add_seconds_b_textview.text.toString().toLongOrNull() ?: 0L)

            val startTimeA = (minutesA + secondsA).toString()
            val startTimeB = (minutesB + secondsB).toString()

            val newTimeSetting = TimeSetting(0, startTimeA, startTimeB)

            mainViewModel.insert(newTimeSetting)
            dismiss()
        }
    }
}