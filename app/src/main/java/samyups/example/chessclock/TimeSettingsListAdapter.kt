package samyups.example.chessclock

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext


class TimeSettingsListAdapter : ListAdapter<TimeSettings, TimeSettingsListAdapter.TimeSettingsViewHolder>(TimeSettingsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSettingsViewHolder {
        return TimeSettingsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TimeSettingsViewHolder, position: Int) {

        val current = getItem(position)
        holder.bind(current)
    }

    class TimeSettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var newTimer : String
        private var startTimeA: Long = 0
        private var startTimeB: Long = 0

        val TimeSettingsItemView: TextView = itemView.findViewById(R.id.textView)


        init {
            TimeSettingsItemView.setOnClickListener { v:View ->


                val startIntent = Intent(TimeSettingsItemView.context, TimerActivity::class.java)



                startIntent.putExtra("startTimeA", startTimeA)
                startIntent.putExtra("startTimeB", startTimeB)
                TimeSettingsItemView.context.startActivity(startIntent)
            }
        }

        companion object {
            fun create(parent: ViewGroup): TimeSettingsViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TimeSettingsViewHolder(view)
            }
        }

        fun bind(timeSettings: TimeSettings) {

            TimeSettingsItemView.text = "Timer A: " + toMinAndSecs(timeSettings.timerA)  + "\n" +
                                        "Timer B: " + toMinAndSecs(timeSettings.timerB)

            startTimeA = timeSettings.timerA.toLong()
            startTimeB = timeSettings.timerB.toLong()



        }

        private fun toMinAndSecs(timer: String): String {


            val minutes : Long = (timer.toLong() / 1000) / 60
            val seconds  = ((timer.toLong() / 1000) % 60).toInt()

            if (seconds < 10) {
                newTimer = "$minutes:0$seconds" // ex: 0:08 instead of 0:8 for 8 seconds
            } else {
                newTimer = "$minutes:$seconds"
            }
            return newTimer
        }


    }

    class TimeSettingsComparator : DiffUtil.ItemCallback<TimeSettings>() {



        override fun areItemsTheSame(oldItem: TimeSettings, newItem: TimeSettings): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TimeSettings, newItem: TimeSettings): Boolean {
            return oldItem == newItem
        }
    }





}


