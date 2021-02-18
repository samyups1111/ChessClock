package samyups.example.chessclock

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    var startTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        oneMin.setOnClickListener {
            startTime = 1L * 60000L // 60,000 millisec = 1 min
            val oneMinIntent = Intent(this, TimerActivity::class.java)
            oneMinIntent.putExtra("startTime", startTime) // sending start time info to TimerActivity
            startActivity(oneMinIntent)
        }

        fiveMin.setOnClickListener {
            startTime = 5 * 60000
            val fiveMinIntent = Intent(this, TimerActivity::class.java)
            fiveMinIntent.putExtra("startTime", startTime)
            startActivity(fiveMinIntent)
        }

        thirtyMin.setOnClickListener {
            startTime = 30 * 60000
            val thirtyMinIntent = Intent(this, TimerActivity::class.java)
            thirtyMinIntent.putExtra("startTime", startTime)
            startActivity(thirtyMinIntent)
        }

        customTime.setOnClickListener {
            val customTimeIntent = Intent(this, CustomTime::class.java)
            startActivity(customTimeIntent)
        }




    }
}