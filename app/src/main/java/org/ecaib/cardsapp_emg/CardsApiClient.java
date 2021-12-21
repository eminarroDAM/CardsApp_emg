package org.ecaib.cardsapp_emg;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CardsApiClient {
    public ArrayList<Card> getCards() {
        String BASE_URL = "https://api.magicthegathering.io/v1/";

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("cards")
                .build();

        String url = builtUri.toString();
        ArrayList<Card> response = doCall(url);

        return response;
    }

    private ArrayList<Card> doCall(String url) {
        try{
            String JsonResponse = HttpUtils.get(url);

            return parseJson(JsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Card> parseJson(String jsonResponse) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            JSONObject cardsResponse = new JSONObject(jsonResponse);
            JSONArray array = cardsResponse.getJSONArray("cards");

            for (int i = 0; i < array.length(); i++) {
                Card card = new Card();

                JSONObject object = array.getJSONObject(i);
                card.setName(object.getString("name"));
                card.setManaCost(object.getString("manaCost"));
                // card.setColors(object.getJSONArray("colors").toString().replace("},{", " ,").split(" "));
                card.setType(object.getString("type"));
                // card.setTypes(object.getJSONArray("types").toString().replace("},{", " ,").split(" "));
                // card.setColors(object.getJSONArray("subtypes").toString().replace("},{", " ,").split(" "));
                card.setRarity(object.getString("rarity"));
                if (object.has("text")){
                    card.setText(object.getString("text"));
                }
                if (object.has("imageUrl")){
                    card.setImageUrl(object.getString("imageUrl"));
                }


                cards.add(card);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cards;
    }
}
