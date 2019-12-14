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
        fun cardUpdated(item: Card)
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

        isEditMode = ((arguments != null) && arguments!!.containsKey(CardsActivity.KEY_TODO))

        if (isEditMode) {
            var card: Card = (arguments?.getSerializable(CardsActivity.KEY_TODO) as Card)
            builder.setTitle("Edit FlashCard")
            etCardQuest.setText(card.cardQuest)
            etCardAns.setText(card.cardAns)
        }

        builder.setPositiveButton("OK") {
                dialog, witch -> // empty
        }

        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etCardQuest.text.isNotEmpty() && etCardAns.text.isNotEmpty()) {
                if(isEditMode){
                    handleCardEdit()
                }else{
                    handleCardCreate()
                }
                dialog.dismiss()
            } else {

                etCardQuest.error = "This field cannot be empty"
                etCardAns.error = "This field cannot be empty"

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

    private fun handleCardEdit() {
        val cardToEdit = arguments?.getSerializable(
            CardsActivity.KEY_TODO
        ) as Card
        cardToEdit.cardQuest = etCardQuest.text.toString()
        cardToEdit.cardAns = etCardAns.text.toString()

        cardHandler.cardUpdated(cardToEdit)
    }
}