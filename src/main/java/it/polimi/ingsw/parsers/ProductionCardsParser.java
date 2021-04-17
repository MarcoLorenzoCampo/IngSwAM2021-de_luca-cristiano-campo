package it.polimi.ingsw.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.market.ProductionCard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public final class ProductionCardsParser {

    /**
     * Parser for the "production_cards_deck.json" file
     *
     * @return List of production cards (the deck)
     */
    public static List<ProductionCard> parseProductionDeck() {

        String productionCardsPath = "src/main/resources/production_cards_deck.json";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(productionCardsPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert bufferedReader != null;
        JsonArray json = new Gson().fromJson(bufferedReader, JsonArray.class);
            return new Gson().fromJson(String.valueOf(json), new TypeToken<List<ProductionCard>>() {
            }.getType());
    }
}

