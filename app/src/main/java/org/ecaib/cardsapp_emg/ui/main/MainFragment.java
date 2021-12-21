package org.ecaib.cardsapp_emg.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.ecaib.cardsapp_emg.Card;
import org.ecaib.cardsapp_emg.CardsAdapter;
import org.ecaib.cardsapp_emg.CardsApiClient;
import org.ecaib.cardsapp_emg.DetailActivity;
import org.ecaib.cardsapp_emg.R;
import org.ecaib.cardsapp_emg.SettingsActivity;
import org.ecaib.cardsapp_emg.SharedViewModel;
import org.ecaib.cardsapp_emg.databinding.MainFragmentBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private ArrayList<Card> items;
    private CardsAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainFragmentBinding binding = MainFragmentBinding.inflate(inflater);
        View view = binding.getRoot();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String rarity = preferences.getString("rarity", "");


        items = new ArrayList<>();

        adapter = new CardsAdapter(
                getContext(),
                R.layout.lv_cards_row,
                R.id.txtTitleRow,
                items
        );

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getCards(rarity).observe(getViewLifecycleOwner(), cards -> {
            adapter.clear();
            adapter.addAll(cards);
        });

        SharedViewModel sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);




        binding.lvCards.setAdapter(adapter);

        binding.lvCards.setOnItemClickListener((parent, view1, position, id) -> {
            Card card = adapter.getItem(position);

            if(!esTablet()) {
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("card", card);

                startActivity(i);
            } else {
                sharedViewModel.select(card);
            }

        });




        return view;
    }

    void refresh() {
        mViewModel.reload();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.refresh) {
            refresh();
        }

        if (id == R.id.settings) {
            Intent i = new Intent(getContext(), SettingsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String rarity = preferences.getString("rarity", "");

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getCards(rarity).removeObservers(getViewLifecycleOwner());


        mViewModel.getCards(rarity).observe(getViewLifecycleOwner(), cards -> {
            adapter.clear();
            adapter.addAll(cards);
        });


    }

    boolean esTablet() {
        return getResources().getBoolean(R.bool.tablet);
    }
}