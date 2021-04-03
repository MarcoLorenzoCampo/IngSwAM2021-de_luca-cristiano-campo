package it.polimi.ingsw.model.market;


import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.parsers.ProductionCardsParser;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marco Lorenzo Campo
 */
public class ProductionCardMarket {

    private final List<ProductionCard> playableProductionCards;
    private List<ProductionCard> availableCards;    /* Each Player sees the available cards only */


    /**
     * @throws FileNotFoundException -- thrown when any path for JSON parsing
     * is incorrect or JSON file is missing.
     */
    public ProductionCardMarket() throws FileNotFoundException {

        ProductionCardsParser productionCardsParser = new ProductionCardsParser();
        playableProductionCards = productionCardsParser.parseProductionDeck();

        Collections.shuffle(playableProductionCards);
        setAvailableCards();
    }


    /**
     * @return availableCards: the only cards player see and can buy
     * are considered "availableCards".
     */
    public List<ProductionCard> getAvailableCards() {

        return availableCards;
    }


    private void setAvailableCards() {
        availableCards = new LinkedList<>(playableProductionCards.stream()
                .collect(Collectors.toMap(ProductionCard::key, color -> color, (f, s) -> f)).values());
    }


    /* prints the cards available */
    public void showAvailableCards() {
        System.out.println(availableCards.toString());
    }

    /**
     * @param boughtCard -- gets replaced the availableCards deck
     * @throws NullPointerException -- thrown if boughtCard can't be replaced
     * with another same (COLOR, LEVEL) card
     */
    private void replaceBoughtCard(ProductionCard boughtCard) throws IndexOutOfBoundsException {

        availableCards.remove(boughtCard);

        //getting a new card from the deck with matching color and level
        availableCards.add(playableProductionCards.
                stream().
                filter(c -> c.getColor().equals(boughtCard.getColor())).
                filter(c -> c.getLevel().equals(boughtCard.getLevel())).
                collect(Collectors.toList()).get(0));
    }


    /**
     * @param boughtCard: when a card is bought, it gets removed from the
     *                  base deck.
     */
    public void buyCard(ProductionCard boughtCard) {

        playableProductionCards.remove(boughtCard);

        try {
            replaceBoughtCard(boughtCard);

        } catch(IndexOutOfBoundsException e) {
            System.out.println("No more cards with signature: level: "
                    + boughtCard.getLevel() + ", color: " + boughtCard.getColor());
        }
    }
}
