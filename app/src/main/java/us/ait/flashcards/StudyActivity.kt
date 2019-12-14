package us.ait.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_study.*
import us.ait.flashcards.adapter.CardAdapter
import us.ait.flashcards.adapter.GroupAdapter
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Card
import us.ait.flashcards.data.Group

class StudyActivity : AppCompatActivity() {

    companion object {
        const val KEY_TODO = "KEY_TODO"
        const val KEY_STARTED = "KEY_STARTED"
    }



    var groupId :Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)


        groupId = intent.getLongExtra("KEY_DETAILS", 0)

        var cards = AppDatabase.getInstance(this@StudyActivity).groupDao().getCards(groupId)




        tvAnsw.text = "Question 1"

        tvQuest.text = "Answer 1"


        btnPrevious.setOnClickListener {

        }

        btnNext.setOnClickListener {

        }


    }




}
