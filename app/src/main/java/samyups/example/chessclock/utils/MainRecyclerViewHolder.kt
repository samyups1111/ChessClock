package samyups.example.chessclock.utils

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import samyups.example.chessclock.R
import samyups.example.chessclock.model.TimeSetting
import samyups.example.chessclock.ui.StartGameActivity

class MainRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val timeSettingTitle: TextView = itemView.findViewById(R.id.time_setting_title)

    fun bind(timeSetting: TimeSetting) {
        val timerA = milliToMinSec(timeSetting.timerA.toLongOrNull()?: 0L)
        val timerB = milliToMinSec(timeSetting.timerB.toLongOrNull()?: 0L)

        val incrementA: Boolean =  timeSetting.incrementA
        val incrementB: Boolean = timeSetting.incrementB
        val delayA: Boolean = timeSetting.delayA
        val delayB: Boolean = timeSetting.delayB
        val incrementTimeA: String = timeSetting.incrementATime
        val incrementTimeB: String = timeSetting.incrementBTime
        val delayATime: String = timeSetting.delayATime
        val delayBTime: String = timeSetting.delayBTime

        fun initUi(incrementA: Boolean, incrementB: Boolean, delayA: Boolean, delayB: Boolean,
                    incrementATime: String, incrementBTime: String, delayATime: String, delayBTime: String) {
            val incA = if (incrementA) "I${incrementATime.toInt() / 1000}" else ""
            val incB = if (incrementB) "I${incrementBTime.toInt() / 1000}" else ""
            val delA = if (delayA) "D${delayATime.toInt() / 1000}" else ""
            val delB = if (delayB) "D${delayBTime.toInt() / 1000}" else ""

            timeSettingTitle.text = "Timer A: $timerA   $delA/$incA\n" +
                                   "Timer B: $timerB   $delB/$incB"
        }



        if (incrementA || incrementB || delayA || delayB) {
            initUi(incrementA, incrementB, delayA, delayB, incrementTimeA, incrementTimeB, delayATime, delayBTime)
        } else {
            timeSettingTitle.text = "Timer A: $timerA\nTimer B: $timerB"
        }



        timeSettingTitle.setOnClickListener {
            val startGameIntent = Intent(itemView.context, StartGameActivity::class.java)
            startGameIntent.putExtra("id", timeSetting.id)
            startGameIntent.putExtra("timerA", timeSetting.timerA.toLong())
            startGameIntent.putExtra("timerB", timeSetting.timerB.toLong())
            startGameIntent.putExtra("delayA", timeSetting.delayA)
            startGameIntent.putExtra("secondsDelayA", timeSetting.delayATime)
            startGameIntent.putExtra("delayB", timeSetting.delayB)
            startGameIntent.putExtra("secondsDelayB", timeSetting.delayBTime)
            startGameIntent.putExtra("incrementA", timeSetting.incrementA)
            startGameIntent.putExtra("incrementSecondsA", timeSetting.incrementATime)
            startGameIntent.putExtra("incrementSecondsB", timeSetting.incrementBTime)
            startGameIntent.putExtra("incrementB", timeSetting.incrementB)
            Log.d("viewHolder", "timeSetting.timerA = ${timeSetting.timerA}")
            itemView.context.startActivity(startGameIntent)
        }
    }


}