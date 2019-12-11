package us.ait.flashcards.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.group_row.view.*
import us.ait.flashcards.CardsActivity
import us.ait.flashcards.MainActivity
import us.ait.flashcards.R
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Group
import java.util.*

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.ViewHolder>{

    var groupList = mutableListOf<Group>()
    var context : Context

    constructor(context: Context, group: List<Group>){
        this.context = context
        groupList.addAll(group)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var groupRow = LayoutInflater.from(context).inflate(
            R.layout.group_row, parent, false
        )

        return ViewHolder(groupRow)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var group = groupList.get(holder.adapterPosition)

        holder.tvTitle.text = group.groupTitle

        holder.btnDelete.setOnClickListener {
            deleteGroup(holder.adapterPosition)
        }

        holder.btnStudy.setOnClickListener {
            // go to study activity
        }

        holder.btnCards.setOnClickListener {
            var intentDetails = Intent()

            intentDetails.setClass(
                context,
                CardsActivity::class.java
            )


            intentDetails.putExtra("KEY_DETAILS", group.groupId)

            context.startActivity(intentDetails)
        }

        holder.btnEdit.setOnClickListener {
//            (context as MainActivity).showEditGroupDialog(
//                group, holder.adapterPosition
//            )
        }

    }

    fun updategroup(group: Group){
        Thread{
            AppDatabase.getInstance(context).groupDao().updateGroup(group)
        }.start()
    }

    fun updateGroupOnPosition(group: Group, index: Int){
        groupList.set(index, group)
        notifyItemChanged(index)
    }

    fun deleteGroup(index: Int){
        Thread{
            AppDatabase.getInstance(context).groupDao().deleteGroup(groupList[index])

            (context as MainActivity).runOnUiThread {
                groupList.removeAt(index)
                notifyItemRemoved(index)

            }
        }.start()

    }

    fun deleteAllGroups() {
        Thread {
            AppDatabase.getInstance(context).groupDao().deleteAllGroup()

            (context as MainActivity).runOnUiThread {
                groupList.clear()
                notifyDataSetChanged()
            }
        }.start()
    }

    fun addGroup(group: Group){
        groupList.add(group)
        notifyItemInserted(groupList.lastIndex)
    }

//    override fun onDimissed(position: Int) {
//        deleteTodo(position)
//    }
//
//    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
//        Collections.swap(todoList, fromPosition, toPosition)
//        notifyItemMoved(fromPosition, toPosition)
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvTitle = itemView.tvTitle
        var btnStudy = itemView.btnStudy
        var btnCards = itemView.btnCards
        var btnEdit = itemView.btnEdit
        var btnDelete = itemView.btnDelete
    }
}