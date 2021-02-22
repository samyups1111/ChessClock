package samyups.example.chessclock

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.time_custom.*

class CustomTime : AppCompatActivity() {

    private var minutesA = 0L
    private var secondsA = 0L
    private var minutesB = 0L
    private var secondsB = 0L
    private var startTimeA = 0L
    private var startTimeB = 0L
    private lateinit var noteViewModel : MySavedTimesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_custom)

        startGame.setOnClickListener {

            minutesA = 60000L * (inputMinutesA.text.toString().toLongOrNull() ?: 0L)
            secondsA = 1000L * (inputSecondsA.text.toString().toLongOrNull() ?: 0L)

            minutesB = 60000L * (inputMinutesB.text.toString().toLongOrNull() ?: 0L)
            secondsB = 1000L * (inputSecondsB.text.toString().toLongOrNull() ?: 0L)

            startTimeA = minutesA + secondsA
            startTimeB = minutesB + secondsB
            val customIntent = Intent(this, TimerActivity::class.java)
            customIntent.putExtra("startTimeA", startTimeA)
            customIntent.putExtra("startTimeB", startTimeB)
            startActivity(customIntent)

        }

        fabSave.setOnClickListener {

        }

        noteViewModel = ViewModelProvider(this) [MySavedTimesViewModel::class.java]


    }
}