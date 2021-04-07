package samyups.example.chessclock.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import samyups.example.chessclock.R
import samyups.example.chessclock.model.TimeSetting

class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewHolder>() {

    var timeSettingList : List<TimeSetting> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent,false)
        return MainRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        val currentTime = timeSettingList[position]
        holder.bind(currentTime)
    }

    override fun getItemCount(): Int {
        return timeSettingList.size
    }

    fun update(list: List<TimeSetting>) {
        this.timeSettingList = list
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {

    }
}


