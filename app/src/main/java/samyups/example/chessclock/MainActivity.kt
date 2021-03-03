package samyups.example.chessclock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var startTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneMinButton.setOnClickListener {

            startTime = 1L * 60000L // 60,000 millisec = 1 min
            val oneMinIntent = Intent(this, TimerActivity::class.java)
            oneMinIntent.putExtra("startTime", startTime) // sending start time info to TimerActivity
            startActivity(oneMinIntent)
        }

        fiveMinButton.setOnClickListener {

            startTime = 5 * 60000
            val fiveMinIntent = Intent(this, TimerActivity::class.java)
            fiveMinIntent.putExtra("startTime", startTime)
            startActivity(fiveMinIntent)
        }

        thirtyMinButton.setOnClickListener {

            startTime = 30 * 60000
            val thirtyMinIntent = Intent(this, TimerActivity::class.java)
            thirtyMinIntent.putExtra("startTime", startTime)
            startActivity(thirtyMinIntent)
        }

        newTimeButton.setOnClickListener {

            val newTimeIntent = Intent(this, SetTimeActivity::class.java)
            startActivity(newTimeIntent)
        }

        customTimesButton.setOnClickListener {

            val customTimesIntent = Intent(this, CustomTimesActivity::class.java)
            startActivity(customTimesIntent)
        }
    }
}