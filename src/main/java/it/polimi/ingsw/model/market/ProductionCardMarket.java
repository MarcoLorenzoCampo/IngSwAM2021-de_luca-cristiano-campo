package it.polimi.ingsw.model.market;


import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.AvailableCardsMessage;
import it.polimi.ingsw.network.messages.serverMessages.EndGameMessage;
import it.polimi.ingsw.network.messages.serverMessages.NoMoreCardsMessage;
import it.polimi.ingsw.parsers.ProductionCardsParser;

import java.util.*;
import java.util.stream.Collectors;

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

        sortAvailableCardsByLevel();
    }

    /**
     * @param boughtCard gets replaced the availableCards deck
     * Use of Optionals to add a card of the same (Level, Color) of the one
     *                   just bought if present;
     */
    private void replaceBoughtCard(ProductionCard boughtCard) {
        availableCards.remove(boughtCard);

        //getting a new card from the deck with matching color and level
        playableProductionCards.
                stream().
                filter(c -> c.getColor().equals(boughtCard.getColor())).
                filter(c -> c.getLevel().equals(boughtCard.getLevel())).
                findFirst().
                ifPresent(availableCards::add);
    }

    /**
     * @param boughtCard: when a card is bought, it gets removed from the
     *                  base deck.
     */
    public void buyCard(ProductionCard boughtCard) throws EndGameException {
        playableProductionCards.remove(boughtCard);
        replaceBoughtCard(boughtCard);
        //PlayingGame.getGameInstance()
        //        .getCurrentPlayer()
        //        .getPlayerBoard()
        //        .increaseBoughCardsCount();

        sortAvailableCardsByLevel();

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
                sortAvailableCardsByLevel();
                break;
            }
        }

        if(availableCards.size() == 11) {
            notifyObserver(new NoMoreCardsMessage());
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
        throw new NullPointerException();
    }

    /**
     * Method to sort the available cards by level when one is removed/replaced.
     */
    private void sortAvailableCardsByLevel() {
        availableCards = availableCards
                .stream()
                .sorted(Comparator.comparing(ProductionCard::getLevel))
                .collect(Collectors.toList());
    }
}
