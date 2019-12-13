package us.ait.flashcards.data

import androidx.room.*

@Dao
interface GroupDao {

    @Query("SELECT * FROM `Group`")
    fun getAllGroup() : List<Group>

    @Insert
    fun addGroup(group: Group) : Long

    @Delete
    fun deleteGroup(group: Group)

    @Update
    fun updateGroup(group: Group)

    @Query("DELETE FROM `Group`")
    fun deleteAllGroup()

    @Query("SELECT * FROM `Card` WHERE parentGroupId = :groupdId")
    fun getCards(groupdId : Long) : List<Card>

    @Insert
    fun addCard(card: Card) : Long

    @Delete
    fun deleteCard(card: Card)

    @Query("DELETE FROM `Card` WHERE parentGroupId = :groupId")
    fun deleteCardsForGroup(groupId: Long)




}