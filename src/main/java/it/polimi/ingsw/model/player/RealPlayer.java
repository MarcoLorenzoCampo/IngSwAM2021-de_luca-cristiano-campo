package it.polimi.ingsw.model.player;

import it.polimi.ingsw.actions.*;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.market.ResourceMarket;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.LeaderCardMessage;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Player Class, ha references to a few useful classes.
 */
public class RealPlayer extends Observable implements Serializable, Visitor {

    private static final long serialVersionUID = -8446287370449348970L;

    /**
     * Name of the player.
     */
    private final String playerName;

    /**
     * Victory points acquired.
     */
    private int victoryPoints;

    /**
     * Reference to the player board.
     */
    private final RealPlayerBoard playerBoard;

    /**
     * Boolean value to know if the player is the first one to play.
     */
    private boolean firstToPlay = false;

    /**
     * Reference to the player state.
     */
    private final PlayerState playerState;

    /**
     * Reference to the owned leader cards.
     */
    private List<LeaderCard> ownedLeaderCards;

    /**
     * test di aggiunta, per adesso li metto globalmente se tutto funziona
     * li faccio passare nel costruttore del player del lobby manager
     */
    private ResourceMarket resourceMarket;
    private ProductionCardMarket productionCardMarket;

    /**
     * Default player constructor.
     * @param name: chosen by the player.
     */
    public RealPlayer(String name) {

        this.playerBoard = new RealPlayerBoard(name);
        this.playerName = name;
        this.victoryPoints = 0;
        this.playerState = new PlayerState();
        this.ownedLeaderCards = new LinkedList<>();
        this.resourceMarket = PlayingGame.getGameInstance().getGameBoard().getResourceMarket();
        this.productionCardMarket = PlayingGame.getGameInstance().getGameBoard().getProductionCardMarket();
    }

    /**
     * Method to remove a leader card and notify the player.
     * @param leaderToDiscard: index of the leader to discard.
     */
    public void discardLeaderCard(int leaderToDiscard) {
        ownedLeaderCards.remove(leaderToDiscard);

        notifyObserver(new LeaderCardMessage(ownedLeaderCards));
    }

    /**
     * Method to remove a leader card and notify the player during the setup phase.
     * @param leaderToDiscard: index of the leader to discard.
     */
    public void setupLeaderCard(int leaderToDiscard) {

        ownedLeaderCards.remove(leaderToDiscard);

        if(ownedLeaderCards.size() == 2) {
            notifyObserver(new LeaderCardMessage(ownedLeaderCards));
        }
    }

    public void setOwnedLeaderCards(List<LeaderCard> ownedLeaderCards) {
        this.ownedLeaderCards = ownedLeaderCards;
    }


    public void setFirstToPlay() {
        this.firstToPlay = true;
    }

    public boolean getIsFirstToPlay() {
        return firstToPlay;
    }

    public String getName() {
        return playerName;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public RealPlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public List<LeaderCard> getOwnedLeaderCards() {
        return ownedLeaderCards;
    }

    @Override
    public void visit(ActivateProductionAction action) {

    }

    @Override
    public void visit(ActivateExtraProductionAction action) {

    }

    @Override
    public void visit(ActivateBaseProductionAction action) {
        if(action.getActionSender().equals(playerName)
        && productionResourceValidator(action.getInput_1())
        && productionResourceValidator(action.getInput_2())
        && productionResourceValidator(action.getOutput())
        && !playerBoard.getProductionBoard().isBaseProductionSelected()){
            playerBoard.getProductionBoard()
                    .selectBaseProduction(action.getInput_1(), action.getInput_2(), action.getOutput());
        }
        //Notify production non available
    }

    @Override
    public void visit(BuyProductionCardAction action) {
        if(action.getActionSender().equals(playerName)
        && !playerState.getHasBoughCard()
        && productionCardRequirementsValidator(action.getBoughtCard())
        && productionSlotValidator(action.getDestinationSlot(), action.getBoughtCard())) {
            try {
                productionCardMarket.buyCard(action.getBoughtCard());
                action.getBoughtCard().placeCard(action.getDestinationSlot(), action.getBoughtCard(), playerBoard.getProductionBoard());
                playerState.setToBeRemoved(action.getBoughtCard().getRequirements());
                playerState.performedExclusiveAction();
                playerBoard.increaseBoughCardsCount();
            } catch (EndGameException e) {
                e.printStackTrace();
                //send notification
            }
        }
    }

    @Override
    public void visit(ChangeMarbleAction action) {

    }

    @Override
    public void visit(DepositAction action) {
        if(action.getActionSender().equals(playerName)
           && (playerBoard.getInventoryManager().getBuffer().size() > action.getIndex())){
            try {
                playerBoard.getInventoryManager().addResourceToWarehouse(action.getIndex());

            } catch (DiscardResourceException exception) {
                playerBoard.getInventoryManager().removeFromBuffer(action.getIndex());
                //notify game of penalty
            }
        }

    }

    @Override
    public void visit(DiscardLeaderCardAction action) {
        if(action.getActionSender().equals(playerName)
        && !playerState.getHasPlacedLeaders()
        && (ownedLeaderCards.size() > action.getLeaderToDiscard())){

            if(playerState.isSetUpPhase()){
                setupLeaderCard(action.getLeaderToDiscard());
            }
            else{
                discardLeaderCard(action.getLeaderToDiscard());
                playerBoard.getFaithTrack().increaseFaithMarker();
            }
        }
    }

    @Override
    public void visit(EndTurnAction action) {

    }

    @Override
    public void visit(ExecuteProductionAction action) {

    }

    @Override
    public void visit(GetResourceFromMarketAction action) {
        if(action.getActionSender().equals(playerName)
            && !playerState.getHasPickedResources()
            && (action.getIndexToPickFrom()>=0)
            && (action.getIndexToPickFrom()<=6)){
            resourceMarket.pickResources(action.getIndexToPickFrom(), playerBoard);
            playerBoard.getInventoryManager().whiteMarblesExchange();
            playerState.performedExclusiveAction();
        }
    }

    @Override
    public void visit(PlaceLeaderAction action) {

    }

    @Override
    public void visit(RearrangeInventoryAction action) {

    }

    @Override
    public void visit(RemoveResourcesAction action) {

    }

    @Override
    public void visit(LorenzoAction action) {

    }


    private boolean productionResourceValidator(ResourceType wantedType){
        if(wantedType.equals(ResourceType.FAITH) || wantedType.equals(ResourceType.UNDEFINED)) return false;
        else return true;
    }

    private boolean productionCardRequirementsValidator(ProductionCard toValidate){
        List<ResourceTag> requirements = toValidate.getRequirements();
        Map<ResourceType, Integer> actualInventory = playerBoard.getInventoryManager().getInventory();

        for (ResourceTag requirement : requirements) {
            if(requirement.getQuantity() > actualInventory.get(requirement.getType()))
                return false;
        }
        return true;
    }

    private boolean productionSlotValidator(int productionSlotIndex, ProductionCard bought){
        return playerBoard.getProductionBoard().checkPutCard(productionSlotIndex, bought);
    }






}
