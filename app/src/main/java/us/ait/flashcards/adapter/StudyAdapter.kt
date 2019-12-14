package us.ait.flashcards.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_row.view.*
import kotlinx.android.synthetic.main.study_card_row.view.*
import us.ait.flashcards.CardsActivity
import us.ait.flashcards.R
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Card
import java.util.*

class StudyAdapter : RecyclerView.Adapter<StudyAdapter.ViewHolder>{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyAdapter.ViewHolder {
                var cardRow = LayoutInflater.from(context).inflate(
            R.layout.study_card_row, parent, false
        )

        return ViewHolder(cardRow)
    }


    override fun onBindViewHolder(holder: StudyAdapter.ViewHolder, position: Int) {

        var card = cardList.get(holder.adapterPosition)

        holder.tvQuest.text = card.cardQuest

        holder.tvAns.text = card.cardAns

        holder.btnShowAnswer.setOnClickListener {

            if(!(holder.tvAns.VISIBLE())) {
                holder.tvAns.setVisible(true)
            }

            else if(holder.tvAns.VISIBLE()){
                holder.tvAns.setVisible(false)
            }
        }

    }

    var cardList = mutableListOf<Card>()
    var context : Context

    constructor(context: Context, todos: List<Card>){
        this.context = context
        cardList.addAll(todos)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

//    }

    override fun getItemCount(): Int {
        return cardList.size
    }


    fun updateTodo(card: Card){
        //TODO figure this out
//        Thread{
//            AppDatabase.getInstance(context).Dao().updateTodo(todo)
//        }.start()
    }

    fun updateTodoOnPosition(card: Card, index: Int){
        cardList.set(index, card)
        notifyItemChanged(index)
    }

    fun deleteCard(index: Int){
        Thread{
            AppDatabase.getInstance(context).groupDao().deleteCard(cardList[index])

            (context as CardsActivity).runOnUiThread {
                cardList.removeAt(index)
                notifyItemRemoved(index)

            }
        }.start()

    }

    fun deleteAllTodos() {
//        Thread {
//            AppDatabase.getInstance(context).todoDao().deleteAllTodo()
//
//            (context as ScrollingActivity).runOnUiThread {
//                todoList.clear()
//                notifyDataSetChanged()
//            }
//        }.start()
    }

    fun addCard(card: Card){
        cardList.add(card)
        notifyItemInserted(cardList.lastIndex)
    }

//    override fun onDimissed(position: Int) {
//        deleteTodo(position)
//
//    }
//
//    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
//        Collections.swap(todoList, fromPosition, toPosition)
//        notifyItemMoved(fromPosition, toPosition)
//    }


    private fun View.VISIBLE(): Boolean{
        return visibility == View.VISIBLE
    }

    fun View.setVisible(visible: Boolean) {
        visibility = if (visible) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvQuest = itemView.tvStudyQuestion
        var tvAns= itemView.tvStudyAnswer
        var btnShowAnswer = itemView.btnStudyAnswer

    }
}