package org.ecaib.cardsapp_emg.ui.main;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.ecaib.cardsapp_emg.AppDatabase;
import org.ecaib.cardsapp_emg.Card;
import org.ecaib.cardsapp_emg.CardDao;
import org.ecaib.cardsapp_emg.CardsApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {
    private final Application app;
    private final AppDatabase appDatabase;
    private final CardDao cardDao;
    private LiveData<List<Card>> movies;

    public MainViewModel(Application application) {
        super(application);

        this.app = application;
        this.appDatabase = AppDatabase.getDatabase(
                this.getApplication());
        this.cardDao = appDatabase.getCardDao();
    }

    public LiveData<List<Card>> getCards(String rarity) {
        return cardDao.getCards(rarity);
    }

    public void reload() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            CardsApiClient api = new CardsApiClient();
            ArrayList<Card> cards = api.getCards();
            cardDao.deleteCards();
            cardDao.addCards(cards);

        });
    }
}