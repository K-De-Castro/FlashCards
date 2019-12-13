package us.ait.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*
import us.ait.flashcards.adapter.GroupAdapter
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Group

class MainActivity : AppCompatActivity(), GroupDialog.GroupHandler {

    companion object {
        const val KEY_TODO = "KEY_TODO"
        const val KEY_STARTED = "KEY_STARTED"
    }

    lateinit var groupAdapter: GroupAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        initRecyclerView()

        fab.setOnClickListener{
            showAddGroupDialog()
        }

        fabDeleteAll.setOnClickListener {
            groupAdapter.deleteAllGroups()
        }


    }

    private fun initRecyclerView() {
        Thread{
            var groups = AppDatabase.getInstance(this@MainActivity).groupDao().getAllGroup()

            runOnUiThread {
                groupAdapter = GroupAdapter(this, groups)
                recyclerTodo.adapter = groupAdapter

                var itemDecorator = DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
                )


                recyclerTodo.addItemDecoration(itemDecorator)


                        recyclerTodo.layoutManager = GridLayoutManager(this,
                            2)

//                val callback = TodoReyclerTouchCallback(todoAdapter)
//                val touchHelper = ItemTouchHelper(callback)
//                touchHelper.attachToRecyclerView(recyclerTodo)
            }
        }.start()
    }

    fun showAddGroupDialog() {
        GroupDialog().show(supportFragmentManager, "TAG_TODO_DIALOG")
    }

    fun saveGroup(group: Group){
        Thread{
            var newID =
                AppDatabase.getInstance(this@MainActivity).groupDao().addGroup(group)

            group.groupId = newID

            runOnUiThread{
                groupAdapter.addGroup(group)
            }
        }.start()
    }

    override fun groupCreated(item: Group) {
        saveGroup(item)
    }
    var editIndex: Int = -1

    override fun groupUpdated(item: Group) {
        Thread{
            AppDatabase.getInstance(
                this@MainActivity).groupDao().updateGroup(item)

            runOnUiThread {
                groupAdapter.updateGroupOnPosition(item, editIndex)
            }
        }.start()
    }
}