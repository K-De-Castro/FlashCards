package us.ait.flashcards.data

import androidx.room.*
import java.io.Serializable

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) var groupId: Long?,
    var groupTitle: String
) : Serializable


@Entity
data class Card(
    @PrimaryKey(autoGenerate = true) var cardId: Long?,
    var parentGroupId: Long?,
    var cardQuest: String,
    var cardAns: String
) : Serializable



data class GroupsWithCards(
    @Embedded var group: Group,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "parentGroupId"
    )
    var cards: List<Card> = emptyList()
)
