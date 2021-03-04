package samyups.example.chessclock

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_set_time.*


class SetTimeActivity : AppCompatActivity() {

    private var minutesA = 0L
    private var secondsA = 0L
    private var minutesB = 0L
    private var secondsB = 0L
    private var startTimeA = 0L
    private var startTimeB = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_time)


        startGameButton.setOnClickListener {

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

            minutesA = 60000L * (inputMinutesA.text.toString().toLongOrNull() ?: 0L)
            secondsA = 1000L * (inputSecondsA.text.toString().toLongOrNull() ?: 0L)

            minutesB = 60000L * (inputMinutesB.text.toString().toLongOrNull() ?: 0L)
            secondsB = 1000L * (inputSecondsB.text.toString().toLongOrNull() ?: 0L)

            startTimeA = minutesA + secondsA
            startTimeB = minutesB + secondsB

            val saveIntent = Intent(this, CustomTimesActivity::class.java)
            saveIntent.putExtra("startTimeA", startTimeA)
            saveIntent.putExtra("startTimeB", startTimeB)
            startActivity(saveIntent)

            if (startTimeA != 0L && startTimeB != 0L)
                Toast.makeText(this, "TIME SAVED", Toast.LENGTH_SHORT).show()
        }
    }
}