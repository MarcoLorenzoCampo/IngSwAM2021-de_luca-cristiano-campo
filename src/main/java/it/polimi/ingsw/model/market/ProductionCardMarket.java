package it.polimi.ingsw.model.market;


import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.parsers.ProductionCardsParser;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.ingsw.enumerations.Level.*;


public class ProductionCardMarket {

    private final List<ProductionCard> playableProductionCards;
    private List<ProductionCard> availableCards;    /* Each Player sees the available cards only */

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
     * Available cards are set when creating the singleton instance of
     * the gameBoard and when rebuilding the deck after Lorenzo discards
     * some cards.
     */
    private void setAvailableCards() {
        availableCards = new LinkedList<> (playableProductionCards
                .stream()
                .collect(Collectors.toMap(ProductionCard::key, color -> color, (f, s) -> f))
                .values());
    }


    /* prints the cards available */
    public void showAvailableCards() {
        System.out.println(availableCards.toString());
    }

    /**
     * @param boughtCard -- gets replaced the availableCards deck
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
        Game.getGameInstance().getCurrentPlayer().getPlayerBoard().increaseBoughCardsCount();
    }

    /**
     * Used during the singleplayer game by Lorenzo il Magnifico.
     * The methods removes two of a specified color, the lowest level available.
     * @param color is the specific color to be removed.
     */
    public void lorenzoRemovesTwo(Color color) {
        int times = 2;

        for(ProductionCard iterator : availableCards) {
            if(iterator.getColor().equals(color)
                    && iterator.getLevel().equals(lowestLevelAvailable(color)) && times>0) {

                playableProductionCards.remove(iterator);
                replaceBoughtCard(iterator);
                times--;
            }
        }
    }

    /**
     * Method to find the lowest level card available for a specific color.
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
}
