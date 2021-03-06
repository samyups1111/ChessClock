package samyups.example.chessclock.ui

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start_game.*
import samyups.example.chessclock.R
import samyups.example.chessclock.utils.milliToMinSec

enum class Time{ HOURS, MINUTES, SECONDS }

class StartGameActivity : AppCompatActivity() {

    private val TAG = "startGameActivity"

    private lateinit var timerA : CountDownTimer
    private lateinit var timerB : CountDownTimer
    private var timerAIsRunning = false
    private var timerBIsRunning = false
    var startTimeA : Long = 0
    var startTimeB : Long = 0
    var gamePaused = false
    private var secondsDelayedA = 6000L
    private var secondsDelayedB = 6000L
    private var secondsIncrementA = 5000L
    private var secondsIncrementB = 5000L
    private var hasTimeDelayA = false
    private var hasTimeDelayB = false
    private lateinit var delayTimerA : CountDownTimer
    private lateinit var delayTimerB : CountDownTimer
    private var incrementA = false
    private var incrementB = false
    var id = 0
    private lateinit var aMinus1 : MenuItem
    private lateinit var aPlus1 : MenuItem
    private lateinit var bMinus1 : MenuItem
    private lateinit var bPlus1 : MenuItem
    private lateinit var pause : MenuItem
    private lateinit var reset: MenuItem
    private lateinit var timeHours : MenuItem
    private lateinit var timeMinutes : MenuItem
    private lateinit var timeSeconds : MenuItem
    var currentTimeType = Time.MINUTES
    var timeToLoseGain = 60_000L
    private lateinit var mMenu : Menu


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate activiated")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        startGame()

    }

    private fun startGame() {
        getTimeSetting()
        applyTimeSetting()
        initButtonA()
        initButtonB()
        initTimeDelayA(secondsDelayedA)
        initTimeDelayB(secondsDelayedB)
    }

    private fun getTimeSetting() {
        Log.d(TAG, "getTimeSetting Activiated")
        id = intent.getIntExtra("id", 0)
        startTimeA = intent.getLongExtra("timerA",0)
        startTimeB = intent.getLongExtra("timerB", 0)
        hasTimeDelayA = intent.getBooleanExtra("delayA", false)
        secondsDelayedA = (intent.getStringExtra("secondsDelayA")?.toLongOrNull() ?: 5000L) + 1000
        hasTimeDelayB = intent.getBooleanExtra("delayB", false)
        secondsDelayedB = (intent.getStringExtra("secondsDelayB")?.toLongOrNull() ?: 5000L) + 1000
        incrementA = intent.getBooleanExtra("incrementA", false)
        secondsIncrementA = intent.getStringExtra("incrementSecondsA")?.toLongOrNull() ?: 5000L
        incrementB = intent.getBooleanExtra("incrementB", false)
        secondsIncrementB = intent.getStringExtra("incrementSecondsB")?.toLongOrNull() ?: 5000L
        Log.d(TAG, "startTimeB = $startTimeB, startTimeA (2)= $startTimeA, id = $id")
        Log.d(TAG, "incrementA = $incrementA, incrementB = $incrementB")
    }

    private fun applyTimeSetting() {
        Log.d(TAG, "applyTimeSetting activated, startTimeA = $startTimeA")
        button_a.text = milliToMinSec(startTimeA)
        button_b.text = milliToMinSec(startTimeB)
    }

    private fun initButtonA() {
        button_a.setOnClickListener {

            Log.d(TAG, "ButtonA clicked: startTimeA = $startTimeA")
            Log.d(TAG, "secondsdelayB = ${secondsDelayedB}")
            if (incrementB) startTimeB += secondsIncrementB
            initTimerB()
            applyTimeSetting()
            if (hasTimeDelayB) {
                initTimeDelayB(secondsDelayedB)
                delayTimerB.start()
            } else {
                timerB.start()
            }
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

            if (incrementA) startTimeA += secondsIncrementA
            // clicking buttonB starts timerA
            initTimerA()
            applyTimeSetting()
            if (hasTimeDelayA) {
                initTimeDelayA(secondsDelayedA)
                delayTimerA.start()

            } else {
                timerA.start()
            }
            button_a.setBackgroundColor(Color.parseColor("#66ff00"))
            button_b.setBackgroundColor(Color.parseColor("#d3d3d3"))
            button_a.isEnabled = true
            button_b.isEnabled = false
            timerAIsRunning = true
            pauseTimerB()
            timerBIsRunning = false
        }
    }

    private fun initTimerA() {
        timerA = object : CountDownTimer(startTimeA, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                button_a.text = milliToMinSec(millisUntilFinished)
                Log.d(TAG, "startTimeA (1) = $startTimeA")
                startTimeA = millisUntilFinished
                Log.d(TAG, "startTimeA (2) = $startTimeA")
            }
            override fun onFinish() {
                button_a.text = "OUT OF TIME!!!"
                button_a.isEnabled = false // otherwise timerB can still run out after winning
                button_b.text = "WINNER!"
                button_b.setBackgroundColor(Color.parseColor("#66ff00"))
                button_a.setBackgroundColor(Color.parseColor("#FF0000"))
            }
        }
    }

    private fun initTimerB() {
        timerB = object : CountDownTimer(startTimeB, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                button_b.text = milliToMinSec(millisUntilFinished)
                startTimeB = millisUntilFinished
            }
            override fun onFinish() {
                button_b.text = "OUT OF TIME!!!"
                button_b.isEnabled = false // otherwise timerA can still run out after winning
                button_a.text = "WINNER!"
                button_a.setBackgroundColor(Color.parseColor("#66ff00"))
                button_b.setBackgroundColor(Color.parseColor("#FF0000"))
            }
        }
    }

    private fun initTimeDelayA(secondsDelayedA: Long) {
        delayTimerA = object: CountDownTimer(secondsDelayedA, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                delay_a.text = milliToMinSec(millisUntilFinished)
            }
            override fun onFinish() {
                timerA.start()
                applyTimeSetting()
            }
        }
    }

    private fun initTimeDelayB(secondsDelayedB: Long) {
        delayTimerB = object: CountDownTimer(secondsDelayedB, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                delay_b.text = milliToMinSec(millisUntilFinished)
            }
            override fun onFinish() {
                timerB.start()
                applyTimeSetting()
            }
        }
    }

    private fun pauseTimerA() {
        if (timerAIsRunning) timerA.cancel()
        if (hasTimeDelayA) delayTimerA.cancel()
    }

    private fun pauseTimerB() {
        if (timerBIsRunning) timerB.cancel()
        if (hasTimeDelayB) delayTimerB.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        mMenu = menu
        aMinus1 = menu.findItem(R.id.a_minus1)
        aPlus1 = menu.findItem(R.id.a_plus1)
        bMinus1 = menu.findItem(R.id.b_minus1)
        bPlus1 = menu.findItem(R.id.b_plus1)
        pause = menu.findItem(R.id.pause)
        reset = menu.findItem(R.id.reset)
        timeHours = menu.findItem(R.id.time_hours)
        timeMinutes = menu.findItem(R.id.time_minutes)
        timeSeconds = menu.findItem(R.id.time_seconds)

        val resetButton = Button(this)
        resetButton.setBackgroundColor(Color.BLACK)
        resetButton.setTextColor(Color.WHITE)
        initResetButton(resetButton)
        initTimeType(menu, currentTimeType)


        reset.actionView = resetButton

        return true
    }



    private fun initResetButton(resetButton: Button) {
        resetButton.text = "RESET"
        resetButton.setOnLongClickListener {
            pauseTimerA()
            pauseTimerB()
            getTimeSetting()
            applyTimeSetting()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pause -> {
                if (gamePaused) {
                    showMenuItem()

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
                    showMenuItem()
                } else {
                    pauseTimerA()
                    pauseTimerB()
                    button_a.isEnabled = false
                    button_b.isEnabled = false
                    gamePaused = true
                    showMenuItem()
                }
                true
            }
            R.id.a_minus1 -> {
                startTimeA -= timeToLoseGain
                if (startTimeA < 0) startTimeA = 0
                applyTimeSetting()
                true
            }
            R.id.a_plus1 -> {
                startTimeA += timeToLoseGain
                applyTimeSetting()
                true
            }
            R.id.b_minus1 -> {
                startTimeB -= timeToLoseGain
                if (startTimeB < 0) startTimeB = 0
                applyTimeSetting()
                true
            }
            R.id.b_plus1 -> {
                startTimeB += timeToLoseGain
                applyTimeSetting()
                true
            }
            R.id.reset -> {
                pauseTimerA()
                pauseTimerB()
                getTimeSetting()
                applyTimeSetting()
                true
            }
            R.id.time_hours -> {
                currentTimeType = Time.HOURS
                initTimeType(mMenu, currentTimeType)
                true
            }

            R.id.time_minutes -> {
                currentTimeType = Time.MINUTES
                initTimeType(mMenu, currentTimeType)
                true
            }
            R.id.time_seconds -> {
                currentTimeType = Time.SECONDS
                initTimeType(mMenu, currentTimeType)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMenuItem() {
        if (gamePaused) {
            aMinus1.isVisible = true
            aPlus1.isVisible = true
            bMinus1.isVisible = true
            bPlus1.isVisible = true
            timeHours.isVisible = true
            timeMinutes.isVisible = true
            timeSeconds.isVisible = true
        } else {
            aMinus1.isVisible = false
            aPlus1.isVisible = false
            bMinus1.isVisible = false
            bPlus1.isVisible = false
            timeHours.isVisible = false
            timeMinutes.isVisible = false
            timeSeconds.isVisible = false
        }
    }

    private fun initTimeType(menu: Menu, timeStatus: Time) {
        //timeHours = menu.findItem(R.id.time_minutes)
        when (timeStatus) {
            Time.SECONDS -> {
                timeHours.title = "Seconds"
                val s = SpannableString(timeHours.title)
                s.setSpan(ForegroundColorSpan(Color.BLUE), 0, s.length, 0)
                timeToLoseGain = 1000L
            }
            Time.MINUTES -> {
                timeHours.title = "Minutes"
                val s = SpannableString(timeHours.title)
                s.setSpan(ForegroundColorSpan(Color.GREEN), 0, s.length, 0)
                timeToLoseGain = 60_000
            }
            Time.HOURS -> {
                timeHours.title = "Hours"
                val s = SpannableString(timeHours.title)
                s.setSpan(ForegroundColorSpan(Color.RED), 0, s.length, 0)
                timeToLoseGain = 3_600_000
            }
        }
    }
}