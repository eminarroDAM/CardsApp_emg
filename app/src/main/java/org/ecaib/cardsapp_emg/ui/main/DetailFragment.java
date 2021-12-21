package org.ecaib.cardsapp_emg.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ecaib.cardsapp_emg.Card;
import org.ecaib.cardsapp_emg.R;
import org.ecaib.cardsapp_emg.SharedViewModel;
import org.ecaib.cardsapp_emg.databinding.MainFragment2Binding;

public class DetailFragment extends Fragment {

    private DetailViewModel mViewModel;
    private MainFragment2Binding binding;
    /*private TextView nameDetail;
    private TextView rarityDetail;
    private TextView manaCostDetail;
    private TextView textDetail;
    private ImageView imageDetail;*/

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragment2Binding.inflate(inflater);
        View view = binding.getRoot();

        /*nameDetail = view.findViewById(R.id.txtNameDetail);
        rarityDetail = view.findViewById(R.id.txtRarityDetail);
        manaCostDetail = view.findViewById(R.id.txtManaCostDetail);
        textDetail = view.findViewById(R.id.txtTextDetail);
        imageDetail = view.findViewById(R.id.imgDetail);*/


        Intent intent = getActivity().getIntent();

        if(intent != null) {
            Card card = (Card)intent.getSerializableExtra("card");

            if (card != null){
                showData(card);
            }
        }

        SharedViewModel sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), this::showData);

        return view;
    }

    private void showData(Card card) {
        binding.txtNameDetail.setText(card.getName());
        binding.txtRarityDetail.setText(card.getRarity());
        binding.txtManaCostDetail.setText(card.getManaCost());
        binding.txtTypeDetail.setText(card.getType());
        binding.txtTextDetail.setText(card.getText());
        Glide.with(getContext()
        ).load(card.getImageUrl()
        ).into(binding.imgDetail);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        // TODO: Use the ViewModel
    }

}