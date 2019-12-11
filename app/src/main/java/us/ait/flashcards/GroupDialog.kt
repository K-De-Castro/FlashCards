package us.ait.flashcards

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.new_group_dialog.view.*
import us.ait.flashcards.data.Group

class GroupDialog : DialogFragment() {

    interface GroupHandler {
        fun groupCreated(item: Group)
        fun groupUpdated(item: Group)
    }

    private lateinit var groupHandler: GroupHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is GroupHandler) {
            groupHandler = context
        } else {
            throw RuntimeException(
                "The activity does not implement the TodoHandlerInterface")
        }
    }

    private lateinit var etSubject: EditText

    var isEditMode = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("New Subject")

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.new_group_dialog, null
        )
        //etTodoDate = rootView.findViewById(R.id.etTodoText)
        etSubject = rootView.etTitle
        builder.setView(rootView)

        isEditMode = ((arguments != null) && arguments!!.containsKey(MainActivity.KEY_TODO))

        if (isEditMode) {
            var group: Group = (arguments?.getSerializable(MainActivity.KEY_TODO) as Group)

            etSubject.setText(group.groupTitle)
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
            if (etSubject.text.isNotEmpty()) {
                if(isEditMode){
                    handleGroupEdit()
                }else{
                    handleGroupCreate()
                }
                dialog.dismiss()
            } else {
                etSubject.error = "This field can not be empty"
            }
        }
    }

    private fun handleGroupCreate() {
        groupHandler.groupCreated(
            Group(
                null,
                etSubject.text.toString()
            )
        )
    }

    private fun handleGroupEdit() {
        val groupToEdit = arguments?.getSerializable(
            MainActivity.KEY_TODO
        ) as Group

        groupToEdit.groupTitle = etSubject.text.toString()

        groupHandler.groupUpdated(groupToEdit)
    }
}