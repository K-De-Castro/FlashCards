package us.ait.flashcards

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.new_card_dialog.view.*
import us.ait.flashcards.data.Card
import java.util.*

class CardDialog : DialogFragment() {

    interface CardHandler {
        fun cardCreated(item: Card)
//        fun todoUpdated(item: Card)
    }

    private var groupId :Long = 0

    private lateinit var cardHandler: CardHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is CardHandler) {
            cardHandler = context
        } else {
            throw RuntimeException(
                "The activity does not implement the TodoHandlerInterface")
        }
    }

    private lateinit var etCardQuest: EditText
    private lateinit var etCardAns: EditText

    var isEditMode = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        groupId = arguments!!.getLong("groupId")

        builder.setTitle("New FlashCard")

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.new_card_dialog, null
        )
        //etTodoDate = rootView.findViewById(R.id.etTodoText)
        etCardAns = rootView.etAnswer
        etCardQuest = rootView.etQuestion
        builder.setView(rootView)

//        isEditMode = ((arguments != null) && arguments!!.containsKey(CardsActivity.KEY_TODO))

//        if (isEditMode) {
//            var card: Todo = (arguments?.getSerializable(ScrollingActivity.KEY_TODO) as Todo)
//
//            etTodoDate.setText(todo.createDate)
//            etTodoText.setText(todo.todoText)
//        }

        builder.setPositiveButton("OK") {
                dialog, witch -> // empty
        }

        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etCardQuest.text.isNotEmpty()) {
                if(isEditMode){
//                    handleTodoEdit()
                }else{
                    handleCardCreate()
                }
                dialog.dismiss()
            } else {
                etCardQuest.error = "This field can not be empty"
            }
        }
    }

    private fun handleCardCreate() {
        cardHandler.cardCreated(
            Card(
                null,
                groupId,
                etCardQuest.text.toString(),
                etCardAns.text.toString()
            )
        )
    }

//    private fun handleTodoEdit() {
//        val todoToEdit = arguments?.getSerializable(
//            CardsActivity.KEY_TODO
//        ) as Todo
//        todoToEdit.createDate = etTodoDate.text.toString()
//        todoToEdit.todoText = etTodoText.text.toString()
//
//        todoHandler.todoUpdated(todoToEdit)
//    }
}