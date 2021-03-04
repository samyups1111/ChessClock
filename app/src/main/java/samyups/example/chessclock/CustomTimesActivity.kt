package samyups.example.chessclock


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_custom_times.*
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.recyclerview_item.*
import samyups.example.chessclock.R.menu.my_saved_times_menu


class CustomTimesActivity : AppCompatActivity() {

    private var startTimeA : Long = 0
    private var startTimeB : Long = 0
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var myAdapter :  TimeSettingsListAdapter

    private val timeSettingsViewModel: TimeSettingsViewModel by viewModels {
        TimeSettingsViewModel.TimeSettingsFactory((application as TimeSettingsApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_times)


        timeSettingsViewModel.allTimes.observe(this, Observer { timeSettings ->
            // Update the cached copy of the words in the adapter.
            timeSettings?.let { myAdapter.submitList(it) }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.myRecyclerView)
        val adapter = TimeSettingsListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        initRecyclerView()
        addDataSet()



    }

    private fun initRecyclerView(){

        myRecyclerView.apply {
            linearLayoutManager = LinearLayoutManager(this@CustomTimesActivity)
            myAdapter= TimeSettingsListAdapter()
            adapter = myAdapter
        }
    }

    private fun addDataSet() {

        startTimeA = intent.getLongExtra("startTimeA", 0)
        startTimeB = intent.getLongExtra("startTimeB", 0)

        val timerA = startTimeA.toString()
        val timerB = startTimeB.toString()
        val newSavedTime = TimeSettings(0, timerA, timerB)

        if (startTimeA != 0L && startTimeB != 0L) timeSettingsViewModel.insert(newSavedTime)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(my_saved_times_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.deleteAll -> {
                timeSettingsViewModel.deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}