package us.ait.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import us.ait.flashcards.adapter.CardAdapter
import us.ait.flashcards.adapter.GroupAdapter
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Card
import us.ait.flashcards.data.Group

class CardsActivity : AppCompatActivity(), CardDialog.CardHandler {

    companion object {
        const val KEY_TODO = "KEY_TODO"
        const val KEY_STARTED = "KEY_STARTED"
    }

    lateinit var  cardAdapter: CardAdapter

    var groupId :Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        initRecyclerView()

        groupId = intent.getLongExtra("KEY_DETAILS", 0)

        fab.setOnClickListener{
            showAddCardDialog()
        }
    }

    private fun initRecyclerView() {
        Thread{
            var cards = AppDatabase.getInstance(this@CardsActivity).groupDao().getCards(groupId)

            runOnUiThread {
                cardAdapter = CardAdapter(this, cards)
                recyclerTodo.adapter = cardAdapter

                var itemDecorator = DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
                recyclerTodo.addItemDecoration(itemDecorator)

                //        recyclerTodo.layoutManager = GridLayoutManager(this,
                //            2)

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

    fun showEditCardDialog(cardToEdit: Card, idx: Int){
        editIndex = idx

        val editDialog = CardDialog()

        val bundle = Bundle()
        bundle.putSerializable(MainActivity.KEY_TODO, cardToEdit)
        editDialog.arguments = bundle

        editDialog.show(supportFragmentManager, "TAG_TODO_EDIT")
    }

    fun saveCard(card: Card){
        Thread{
            var newID =
                AppDatabase.getInstance(this@CardsActivity).groupDao().addCard(card)

            card.cardId = newID

            runOnUiThread{
                cardAdapter.addCard(card)
            }
        }.start()
    }

    override fun cardCreated(item: Card) {
        saveCard(item)
    }
    var editIndex: Int = -1

    override fun cardUpdated(item: Card) {
        Thread{
            AppDatabase.getInstance(
                this@CardsActivity).groupDao().updateCard(item)

            runOnUiThread {
                cardAdapter.updateCardOnPosition(item, editIndex)
            }
        }.start()
    }
}
