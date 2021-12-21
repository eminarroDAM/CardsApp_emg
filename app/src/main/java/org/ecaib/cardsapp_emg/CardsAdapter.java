package org.ecaib.cardsapp_emg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import org.ecaib.cardsapp_emg.databinding.LvCardsRowBinding;

import java.util.List;

public class CardsAdapter extends ArrayAdapter<Card> {
    private LvCardsRowBinding binding;

    public CardsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Card> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Card card = getItem(position);

        if(convertView == null){
            binding = LvCardsRowBinding.inflate(
                    LayoutInflater.from(getContext()),
                    parent,
                    false
            );
        } else {
            binding = LvCardsRowBinding.bind(convertView);
        }


        binding.txtTitleRow.setText(card.getName());
        binding.txtRarity.setText(card.getRarity());
        binding.txtType.setText(card.getType());
        Glide.with(getContext()
        ).load(card.getImageUrl()
        ).into(binding.cardImageRow);

        return binding.getRoot();
    }
}
