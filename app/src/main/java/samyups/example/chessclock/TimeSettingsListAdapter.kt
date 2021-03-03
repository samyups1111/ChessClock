package samyups.example.chessclock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


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

        private val TimeSettingsItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(timeSettings: TimeSettings) {
            TimeSettingsItemView.text = "Timer A: " + toMinAndSecs(timeSettings.timerA)  + "\n" +
                                        "Timer B: " + toMinAndSecs(timeSettings.timerB)
        }

        fun toMinAndSecs(timer: String): String {


            val minutes : Long = (timer.toLong() / 1000) / 60
            val seconds  = ((timer.toLong() / 1000) % 60).toInt()

            if (seconds < 10) {
                newTimer = "$minutes:0$seconds" // ex: 0:08 instead of 0:8 for 8 seconds
            } else {
                newTimer = "$minutes:$seconds"
            }
            return newTimer
        }

        companion object {
            fun create(parent: ViewGroup): TimeSettingsViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TimeSettingsViewHolder(view)
            }
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