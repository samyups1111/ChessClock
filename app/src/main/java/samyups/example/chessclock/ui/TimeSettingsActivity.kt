package samyups.example.chessclock.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_time_settings.*
import kotlinx.android.synthetic.main.recyclerview_item.*
import samyups.example.chessclock.*
import samyups.example.chessclock.utils.MainApplication
import samyups.example.chessclock.utils.MainRecyclerViewAdapter
import samyups.example.chessclock.utils.MainViewModelFactory

class TimeSettingsActivity : AppCompatActivity() {

    private val mainAdapter = MainRecyclerViewAdapter()
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as MainApplication).repository)
    }
    private lateinit var saveTimeDialog: SaveTimeDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_settings)
        observeData()
        initRecyclerView()
        initUI()
    }

    private fun initUI() {
        add_time_setting_fab.setOnClickListener {
            showSaveTimeDialog(mainViewModel)
        }
    }

    private fun observeData() {
        mainViewModel.timeSettingsListLiveData.observe(this, Observer { list ->
            mainAdapter.update(list)
        })
    }

    private fun initRecyclerView(){
        main_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@TimeSettingsActivity)
            adapter = mainAdapter
        }
        initItemTouchHelper(main_recyclerview)
    }

    private fun showSaveTimeDialog(mainViewModel: MainViewModel) {
        saveTimeDialog = SaveTimeDialog(mainViewModel)
        saveTimeDialog.show(supportFragmentManager, "setTimeDialog")
    }

    private fun initItemTouchHelper(mainRecyclerView: RecyclerView) {
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or
                     ItemTouchHelper.DOWN,
                     ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                mainRecyclerView.adapter?.notifyItemMoved(
                    viewHolder.adapterPosition,
                    target.adapterPosition
                )
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val timeSettingsList = mainAdapter.timeSettingList
                mainViewModel.delete(timeSettingsList[viewHolder.adapterPosition])
                mainAdapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(mainRecyclerView)
    }
}