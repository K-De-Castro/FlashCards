package us.ait.flashcards

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_study.*
import kotlinx.android.synthetic.main.group_row.*
import us.ait.flashcards.adapter.CardAdapter
import us.ait.flashcards.adapter.GroupAdapter
import us.ait.flashcards.data.AppDatabase
import us.ait.flashcards.data.Card
import us.ait.flashcards.data.Group
import kotlin.concurrent.thread

class StudyActivity : AppCompatActivity() {

    companion object {
        const val KEY_TODO = "KEY_TODO"
        const val KEY_STARTED = "KEY_STARTED"
    }


    var currentCard :Int = -1


    var groupId :Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)


        var groupId = intent.getLongExtra("KEY_DETAILS", 0)


        Thread(Runnable {

            var cards = AppDatabase.getInstance(this@StudyActivity).groupDao().getCards(groupId)

            if(cards.size>0){

            buttonStart(cards)

            buttonNext(cards)

            buttonPrevious(cards)

            btnAnswer.setOnClickListener {
                tvAnsw.setVisibility(VISIBLE)
            }

            } else{
                tvAnsw.text = ""
                tvQuest.text = "Empty"
                btnStart.setVisibility(GONE)

            }


            this@StudyActivity.runOnUiThread(java.lang.Runnable {
            })
        }).start()


        }

    private fun buttonPrevious(cards: List<Card>) {
        btnPrevious.setOnClickListener {


            if (currentCard > 0) {

                currentCard -= 1

                tvAnsw.text = cards.get(currentCard).cardAns

                tvQuest.text = cards.get(currentCard).cardQuest

                tvAnsw.setVisibility(INVISIBLE)
            }
        }
    }

    private fun buttonNext(cards: List<Card>) {
        btnNext.setOnClickListener {


            if (currentCard + 1 < cards.size) {

                currentCard += 1

                tvAnsw.text = cards.get(currentCard).cardAns

                tvQuest.text = cards.get(currentCard).cardQuest

                tvAnsw.setVisibility(INVISIBLE)

            }

        }
    }

    private fun buttonStart(cards: List<Card>) {
        btnStart.setOnClickListener {

            tvAnsw.text = cards.get(currentCard + 1).cardAns

            tvQuest.text = cards.get(currentCard + 1).cardQuest

            currentCard += 1

            btnStart.setVisibility(GONE)
            btnPrevious.setVisibility(VISIBLE)
            btnNext.setVisibility(VISIBLE)
            btnAnswer.setVisibility(VISIBLE)
        }
    }

}





