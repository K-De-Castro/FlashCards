package us.ait.flashcards.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_row.view.*
import us.ait.flashcards.CardsActivity
import us.ait.flashcards.R
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Card
import java.util.*

class CardAdapter : RecyclerView.Adapter<CardAdapter.ViewHolder>{

    var cardList = mutableListOf<Card>()
    var context : Context

    constructor(context: Context, cards: List<Card>){
        this.context = context
        cardList.addAll(cards)
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

        holder.btnDelete.setOnClickListener{
            deleteCard(holder.adapterPosition)
        }

        holder.btnEdit.setOnClickListener {
            (context as CardsActivity).showEditCardDialog(
                card, holder.adapterPosition
            )
        }

    }

    fun updateCard(card: Card){
        Thread{
            AppDatabase.getInstance(context).groupDao().updateCard(card)
        }.start()
    }

    fun updateCardOnPosition(card: Card, index: Int){
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
        var tvQuest = itemView.tvQuestion
        var tvAns= itemView.tvAnswer
        var btnDelete = itemView.btnDeleteCardRow
        var btnEdit = itemView.btnEditCardRow
        var btnShowAnswer = itemView.btnShowAnswer

    }
}