package org.ecaib.cardsapp_emg;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDao {
    @Query("select * from card where rarity like '%' || :rarity || '%'")
    LiveData<List<Card>> getCards(String rarity);

    @Insert
    void addCards(List<Card> cards);

    @Delete
    void deleteCard(Card card);

    @Query("DELETE FROM card")
    void deleteCards();
}
