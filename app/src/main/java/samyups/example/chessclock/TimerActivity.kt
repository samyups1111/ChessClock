package samyups.example.chessclock

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import kotlinx.android.synthetic.main.activity_timer.*



class TimerActivity : AppCompatActivity() {

    private lateinit var timerA : CountDownTimer
    private lateinit var timerB : CountDownTimer
    private var timerAIsRunning = false
    private var timerBIsRunning = false
    var startTimeA : Long = 0
    var startTimeB : Long = 0
    var gamePaused = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        // Get starting time info from MainActivity/CustomTime
        startTimeA = intent.getLongExtra("startTime", 0)
        startTimeB = intent.getLongExtra("startTime", 0)

        if (startTimeA == 0L) startTimeA = intent.getLongExtra("startTimeA", 0)
        if (startTimeB == 0L) startTimeB = intent.getLongExtra("startTimeB", 0)

        timerUI(startTimeA, buttonA)
        timerUI(startTimeB, buttonB)


        buttonB.setOnClickListener {
            // clicking buttonB starts timerA
            timerA = object : CountDownTimer(startTimeA, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerUI(millisUntilFinished, buttonA)
                    startTimeA = millisUntilFinished
                }

                override fun onFinish() {
                    buttonA.text = "OUT OF TIME!!!"
                    buttonA.isEnabled = false // otherwise timerB can still run out after winning
                    buttonB.text = "WINNER!"
                    buttonB.setBackgroundColor(Color.parseColor("#66ff00"))
                    buttonA.setBackgroundColor(Color.parseColor("#FF0000"))
                }


            }.start()
            buttonA.setBackgroundColor(Color.parseColor("#66ff00"))
            buttonB.setBackgroundColor(Color.parseColor("#d3d3d3"))
            buttonA.isEnabled = true
            buttonB.isEnabled = false
            timerAIsRunning = true
            pauseTimerB()
            timerBIsRunning = false
        }

        buttonA.setOnClickListener {
            timerB = object : CountDownTimer(startTimeB, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerUI(millisUntilFinished, buttonB)
                    startTimeB = millisUntilFinished

                }

                override fun onFinish() {
                    buttonB.text = "OUT OF TIME!!!"
                    buttonB.isEnabled = false // otherwise timerA can still run out after winning
                    buttonA.text = "WINNER!"
                    buttonA.setBackgroundColor(Color.parseColor("#66ff00"))
                    buttonB.setBackgroundColor(Color.parseColor("#FF0000"))
                }
            }.start()
            buttonA.setBackgroundColor(Color.parseColor("#d3d3d3"))
            buttonB.setBackgroundColor(Color.parseColor("#66ff00"))
            buttonB.isEnabled = true
            buttonA.isEnabled = false
            timerBIsRunning = true
            pauseTimerA()
            timerAIsRunning = false
        }
    }

    private fun pauseTimerA() {
        if (timerAIsRunning) timerA.cancel()
    }

    private fun pauseTimerB() {
        if (timerBIsRunning) timerB.cancel()
    }

    fun timerUI(startTime : Long, button : Button) {
        val minute : Long = (startTime / 1000) / 60
        val seconds  = ((startTime / 1000) % 60).toInt()

        if (seconds < 10) {
            button.text = "$minute:0$seconds" // ex: 0:08 instead of 0:8 for 8 seconds
        } else {
            button.text = "$minute:$seconds"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.pause -> {
                if (gamePaused) {
                    if (timerAIsRunning) {  // So we know whose turn it was before the game was paused
                        buttonA.isEnabled = true
                        buttonB.performClick()
                        gamePaused = false
                    } else if (timerBIsRunning) {
                        buttonB.isEnabled = true
                        buttonA.performClick()
                        gamePaused = false
                    } else {              // Need this for when paused is clicked before the game starts
                        buttonA.isEnabled = true
                        buttonB.isEnabled = true
                        gamePaused = false
                    }
                } else {
                    pauseTimerA()
                    pauseTimerB()
                    buttonA.isEnabled = false
                    buttonB.isEnabled = false
                    gamePaused = true




                }
                true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}