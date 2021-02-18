package samyups.example.chessclock

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.time_custom.*
import java.lang.NumberFormatException

class CustomTime : AppCompatActivity() {

    private var minutes = 0L
    private var seconds = 0L
    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_custom)

        startGame.setOnClickListener {

            //I used try/catch so an empty user input doesn't crash the app

            try {
                minutes = (60000 * inputMinutes.text.toString().toInt()).toLong() // 60000 millisec = 1 min

            } catch (exception : NumberFormatException) {
                minutes = 0L

            }
            try {
                seconds = (1000 * inputSeconds.text.toString().toInt()).toLong()
            } catch (exception : NumberFormatException) {
                seconds = 0L
            }

            startTime = minutes + seconds
            val customIntent = Intent(this, TimerActivity::class.java)
            customIntent.putExtra("startTime", startTime)
            startActivity(customIntent)

        }


    }
}