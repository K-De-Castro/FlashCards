package us.ait.flashcards.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_row.view.*
import us.ait.flashcards.R
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Card
import java.util.*

class CardAdapter : RecyclerView.Adapter<CardAdapter.ViewHolder>{

    var cardList = mutableListOf<Card>()
    var context : Context

    constructor(context: Context, todos: List<Card>){
        this.context = context
        cardList.addAll(todos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var cardRow = LayoutInflater.from(context).inflate(
            R.layout.card_row, parent, false
        )

        return ViewHolder(cardRow)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var card = cardList.get(holder.adapterPosition)

        holder.tvAns.text = card.cardAns
        holder.tvQuest.text = card.cardQuest

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

    fun deleteTodo(index: Int){
//        Thread{
//            AppDatabase.getInstance(context).todoDao().deleteTodo(todoList[index])
//
//            (context as ScrollingActivity).runOnUiThread {
//                todoList.removeAt(index)
//                notifyItemRemoved(index)
//
//            }
//        }.start()

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvQuest = itemView.tvQuestion
        var tvAns= itemView.tvAnswer

    }
}