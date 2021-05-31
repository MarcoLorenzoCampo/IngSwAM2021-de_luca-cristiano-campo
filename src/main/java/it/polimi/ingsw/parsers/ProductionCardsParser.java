package it.polimi.ingsw.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.market.ProductionCard;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public final class ProductionCardsParser {

    /**
     * Parser for the "production_cards_deck.json" file
     *
     * @return List of production cards (the deck)
     */
    public static List<ProductionCard> parseProductionDeck() {

        String productionCardsPath = "/production_cards_deck.json";
        Reader reader;

        reader = new InputStreamReader(Objects.requireNonNull(ProductionCardsParser.class.getResourceAsStream(productionCardsPath)),
                StandardCharsets.UTF_8);

        JsonArray json = new Gson().fromJson(reader, JsonArray.class);
            return new Gson().fromJson(String.valueOf(json), new TypeToken<List<ProductionCard>>() {
            }.getType());
    }
}

