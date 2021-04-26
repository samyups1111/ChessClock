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

        val incrementA =  timeSetting.incrementA
        val incrementB = timeSetting.incrementB
        val delayA = timeSetting.delayA
        val delayB = timeSetting.delayB

        fun initUi(incrementA: Boolean, incrementB: Boolean, delayA: Boolean, delayB: Boolean) {
            val incA = if (incrementA) "I" else ""
            val incB = if (incrementB) "I" else ""
            val delA = if (delayA) "D" else ""
            val delB = if (delayB) "D" else ""

            timeSettingTitle.text = "Timer A: $timerA   $delA/$incA\nTimer B: $timerB   $delB/$incB"
        }



        if (incrementA || incrementB || delayA || delayB) {
            initUi(incrementA, incrementB, delayA, delayB)
        } else {
            timeSettingTitle.text = "Timer A: $timerA\nTimer B: $timerB"
        }



        timeSettingTitle.setOnClickListener {
            val startGameIntent = Intent(itemView.context, StartGameActivity::class.java)
            startGameIntent.putExtra("id", timeSetting.id)
            startGameIntent.putExtra("timerA", timeSetting.timerA.toLong())
            startGameIntent.putExtra("timerB", timeSetting.timerB.toLong())
            startGameIntent.putExtra("delayA", timeSetting.delayA)
            startGameIntent.putExtra("delayB", timeSetting.delayB)
            startGameIntent.putExtra("incrementA", timeSetting.incrementA)
            startGameIntent.putExtra("incrementB", timeSetting.incrementB)
            Log.d("viewHolder", "timeSetting.timerA = ${timeSetting.timerA}")
            itemView.context.startActivity(startGameIntent)
        }
    }


}