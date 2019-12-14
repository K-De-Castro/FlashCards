package us.ait.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import us.ait.flashcards.adapter.CardAdapter
import us.ait.flashcards.adapter.GroupAdapter
import us.ait.flashcards.adapter.StudyAdapter
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Card
import us.ait.flashcards.data.Group

class StudyActivity : AppCompatActivity() {

    companion object {
        const val KEY_TODO = "KEY_TODO"
        const val KEY_STARTED = "KEY_STARTED"
    }

    lateinit var  studyAdapter: StudyAdapter

    var groupId :Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        initRecyclerView()

        groupId = intent.getLongExtra("KEY_DETAILS", 0)


    }

    private fun initRecyclerView() {
        Thread{
            var cards = AppDatabase.getInstance(this@StudyActivity).groupDao().getCards(groupId)

            runOnUiThread {
                studyAdapter = StudyAdapter(this, cards)
                recyclerTodo.adapter = studyAdapter

                var itemDecorator = DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
                recyclerTodo.addItemDecoration(itemDecorator)



//                val callback = TodoReyclerTouchCallback(todoAdapter)
//                val touchHelper = ItemTouchHelper(callback)
//                touchHelper.attachToRecyclerView(recyclerTodo)
            }
        }.start()
    }

    fun showAddCardDialog() {
        var dialog = CardDialog()
        var arg = Bundle()
        arg?.putLong("groupId", groupId)
        dialog.arguments = arg
        dialog.show(supportFragmentManager, "TAG_TODO_DIALOG")
    }

    fun saveCard(card: Card){
        Thread{
            var newID =
                AppDatabase.getInstance(this@StudyActivity).groupDao().addCard(card)

            card.cardId = newID

            runOnUiThread{
                studyAdapter.addCard(card)
            }
        }.start()
    }


//    var editIndex: Int = -1

//    override fun groupUpdated(item: Group) {
//        Thread{
//            AppDatabase.getInstance(
//                this@MainActivity).groupDao().updateGroup(item)
//
//            runOnUiThread {
//                groupAdapter.updateGroupOnPosition(item, editIndex)
//            }
//        }.start()
//    }
}
