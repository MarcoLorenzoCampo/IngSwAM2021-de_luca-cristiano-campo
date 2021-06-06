package it.polimi.ingsw.model.market;


import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.AvailableCardsMessage;
import it.polimi.ingsw.network.messages.serverMessages.NoMoreCardsMessage;
import it.polimi.ingsw.parsers.ProductionCardsParser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.polimi.ingsw.enumerations.Level.*;

/**
 * Class containing the production card deck and method to buy/discard cards.
 */
public class ProductionCardMarket extends Observable {

    /**
     * Whole production cards deck.
     */
    private final List<ProductionCard> playableProductionCards;

    /**
     * List of cards available. There are the only cards player can see and buy.
     */
    private List<ProductionCard> availableCards;

    /**
     * When the game stars, the market is created by the production cards (development cards)
     * parsing from JSON, and setting the available ones, sorted by level.
     */
    public ProductionCardMarket() {
        playableProductionCards = ProductionCardsParser.parseProductionDeck();
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

    /**
     * Available cards are set and sorted by level when creating the
     * singleton instance of the gameBoard
     */
    private void setAvailableCards() {
        availableCards = new LinkedList<> (playableProductionCards
                .stream()
                .collect(Collectors.toMap(ProductionCard::key, color -> color, (f, s) -> f))
                .values());

        sortAvailableCards();
    }

    /**
     * @param boughtCard gets replaced the availableCards deck
     * Use of Optionals to add a card of the same (Level, Color) of the one
     *                   just bought if present;
     */
    private void replaceBoughtCard(ProductionCard boughtCard) {
        availableCards.remove(boughtCard);

        //getting a new card from the deck with matching color and level
        playableProductionCards
                .stream()
                .filter(c -> c.getColor().equals(boughtCard.getColor()))
                .filter(c -> c.getLevel().equals(boughtCard.getLevel()))
                .findFirst()
                .ifPresent(availableCards::add);
    }

    /**
     * @param boughtCard: when a card is bought, it gets removed from the
     *                  base deck.
     */
    public void buyCard(ProductionCard boughtCard) {
        playableProductionCards.remove(boughtCard);
        replaceBoughtCard(boughtCard);

        sortAvailableCards();

        notifyObserver(new AvailableCardsMessage(availableCards));
    }

    /**
     * Used during the single player game by Lorenzo.
     * The methods removes two of a specified color, the lowest level available.
     * @param color is the specific color to be removed.
     */
    public void lorenzoRemoves(Color color) {
        for(ProductionCard iterator : availableCards) {
            if(iterator.getColor().equals(color)
                    && iterator.getLevel().equals(lowestLevelAvailable(color))) {

                playableProductionCards.remove(iterator);
                replaceBoughtCard(iterator);
                sortAvailableCards();
                break;
            }
        }

        if(colorNotAvailable()) {
            notifyControllerObserver(new NoMoreCardsMessage());
        }
        notifyObserver(new AvailableCardsMessage(availableCards));
    }

    /**
     * Method to find the lowest level card available for a specific color. Available cards
     * are sorted by level, so the first instance of a card is also the lowest level available.
     * @param color, specific color to look for.
     * @return the lowest level available.
     */
    private Level lowestLevelAvailable(Color color) {
        for(ProductionCard iterator : availableCards) {
            if(iterator.getColor().equals(color)) {
                switch (iterator.getLevel()) {
                    case ONE : return ONE;
                    case TWO : return TWO;
                    case THREE : return THREE;
                }
            }
        }
        return ANY;
    }

    /**
     * Method to sort the available cards by level and color when one is removed/replaced.
     */
    private void sortAvailableCards() {

        List<ProductionCard> level1 = availableCards
                .stream()
                .filter(p -> p.getLevel().equals(ONE))
                .sorted(Comparator.comparing((ProductionCard::getColor)))
                .collect(Collectors.toList());

        List<ProductionCard> level2 = availableCards
                .stream()
                .filter(p -> p.getLevel().equals(TWO))
                .sorted(Comparator.comparing((ProductionCard::getColor)))
                .collect(Collectors.toList());

        List<ProductionCard> level3 = availableCards
                .stream()
                .filter(p -> p.getLevel().equals(THREE))
                .sorted(Comparator.comparing((ProductionCard::getColor)))
                .collect(Collectors.toList());

        availableCards = Stream.concat(level1.stream(), level2.stream()).collect(Collectors.toList());
        availableCards = Stream.concat(availableCards.stream(), level3.stream()).collect(Collectors.toList());
    }

    /**
     * Method to check if the single player end game conditions are matched.
     * @return: boolean to check if the game has to be ended.
     */
    private boolean colorNotAvailable() {
        boolean green = false;
        boolean blue = false;
        boolean yellow = false;
        boolean purple = false;

        for(ProductionCard p : availableCards) {
            if(p.getColor().equals(Color.GREEN)) green = true;
            else if(p.getColor().equals(Color.BLUE)) blue = true;
            else if(p.getColor().equals(Color.PURPLE)) purple = true;
            else if(p.getColor().equals(Color.YELLOW)) yellow = true;
        }

        return !(green && blue && yellow && purple);
    }
}
