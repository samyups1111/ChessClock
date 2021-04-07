package samyups.example.chessclock.ui

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start_game.*
import samyups.example.chessclock.R

class StartGameActivity : AppCompatActivity() {

    private lateinit var timerA : CountDownTimer
    private lateinit var timerB : CountDownTimer
    private var timerAIsRunning = false
    private var timerBIsRunning = false
    var startTimeA : Long = 0
    var startTimeB : Long = 0
    var gamePaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)
        startGame()
    }

    private fun startGame() {
        getTimeSetting()
        initButtonA()
        initButtonB()
    }

    private fun getTimeSetting() {
        startTimeA = intent.getLongExtra("startTime", 0)
        startTimeB = intent.getLongExtra("startTime", 0)

        if (startTimeA == 0L) startTimeA = intent.getLongExtra("startTimeA", 0)
        if (startTimeB == 0L) startTimeB = intent.getLongExtra("startTimeB", 0)

        timerUI(startTimeA, button_a)
        timerUI(startTimeB, button_b)
    }

    private fun initButtonA() {
        button_a.setOnClickListener {
            timerB = object : CountDownTimer(startTimeB, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerUI(millisUntilFinished, button_b)
                    startTimeB = millisUntilFinished
                }
                override fun onFinish() {
                    button_b.text = "OUT OF TIME!!!"
                    button_b.isEnabled = false // otherwise timerA can still run out after winning
                    button_a.text = "WINNER!"
                    button_a.setBackgroundColor(Color.parseColor("#66ff00"))
                    button_b.setBackgroundColor(Color.parseColor("#FF0000"))
                }
            }.start()
            button_a.setBackgroundColor(Color.parseColor("#d3d3d3"))
            button_b.setBackgroundColor(Color.parseColor("#66ff00"))
            button_b.isEnabled = true
            button_a.isEnabled = false
            timerBIsRunning = true
            pauseTimerA()
            timerAIsRunning = false
        }
    }

    private fun initButtonB() {
        button_b.setOnClickListener {
            // clicking buttonB starts timerA
            timerA = object : CountDownTimer(startTimeA, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerUI(millisUntilFinished, button_a)
                    startTimeA = millisUntilFinished
                }
                override fun onFinish() {
                    button_a.text = "OUT OF TIME!!!"
                    button_a.isEnabled = false // otherwise timerB can still run out after winning
                    button_b.text = "WINNER!"
                    button_b.setBackgroundColor(Color.parseColor("#66ff00"))
                    button_a.setBackgroundColor(Color.parseColor("#FF0000"))
                }
            }.start()
            button_a.setBackgroundColor(Color.parseColor("#66ff00"))
            button_b.setBackgroundColor(Color.parseColor("#d3d3d3"))
            button_a.isEnabled = true
            button_b.isEnabled = false
            timerAIsRunning = true
            pauseTimerB()
            timerBIsRunning = false
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
                        button_a.isEnabled = true
                        button_b.performClick()
                        gamePaused = false
                    } else if (timerBIsRunning) {
                        button_b.isEnabled = true
                        button_a.performClick()
                        gamePaused = false
                    } else {              // Need this for when paused is clicked before the game starts
                        button_a.isEnabled = true
                        button_b.isEnabled = true
                        gamePaused = false
                    }
                } else {
                    pauseTimerA()
                    pauseTimerB()
                    button_a.isEnabled = false
                    button_b.isEnabled = false
                    gamePaused = true
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}