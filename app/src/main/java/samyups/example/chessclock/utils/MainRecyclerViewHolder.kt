package samyups.example.chessclock.utils

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import samyups.example.chessclock.R
import samyups.example.chessclock.model.TimeSetting
import samyups.example.chessclock.ui.StartGameActivity

class MainRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val timeSettingTitle: TextView = itemView.findViewById(R.id.time_setting_title)

    fun bind(timeSetting: TimeSetting) {
        val timerA = milliToMinSec(timeSetting.timerA.toLong())
        val timerB = milliToMinSec(timeSetting.timerB.toLong())
        timeSettingTitle.text = "Timer A: $timerA\nTimer B: $timerB"

        timeSettingTitle.setOnClickListener {
            val startGameIntent = Intent(itemView.context, StartGameActivity::class.java)
            startGameIntent.putExtra("startTimeA", timeSetting.timerA.toLong())
            startGameIntent.putExtra("startTimeB", timeSetting.timerB.toLong())
            itemView.context.startActivity(startGameIntent)
        }
    }
}