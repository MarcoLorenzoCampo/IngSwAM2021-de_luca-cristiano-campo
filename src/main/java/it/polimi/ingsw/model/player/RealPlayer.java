package it.polimi.ingsw.model.player;

import it.polimi.ingsw.actions.*;
import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.market.ResourceMarket;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.DiscardedResourceMessage;
import it.polimi.ingsw.network.messages.serverMessages.LeaderCardMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.enumerations.ResourceType.UNDEFINED;

/**
 * Player Class, ha references to a few useful classes.
 */
public class RealPlayer extends Observable implements Visitor {

    /**
     * Name of the player.
     */
    private final String playerName;


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

    private final ResourceMarket resourceMarket;
    private final ProductionCardMarket productionCardMarket;

    /**
     * Default player constructor.
     * @param name: chosen by the player.
     */
    public RealPlayer(String name) {

        this.playerBoard = new RealPlayerBoard(name);
        this.playerName = name;
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

        if(ownedLeaderCards.size() != 0) {
            notifyObserver(new LeaderCardMessage(ownedLeaderCards));
        }
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

    public RealPlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public List<LeaderCard> getOwnedLeaderCards() {
        return ownedLeaderCards;
    }

    public int computeTotalVictoryPoints() {

        int victoryPoints = 0;

        int pointsFromLeaders = 0;
        int pointsFromResources = 0;
        int pointsFromFaithTrack = 0;
        int pointsFromProductions = 0;

        //Points from faith track
        pointsFromFaithTrack += playerBoard.getFaithTrack().computeFaithTrackPoints();

        //Points from bought production cards.
        pointsFromProductions += playerBoard.getProductionBoard().getVictoryPoints();

        //Points from activated leader cards.
        for (LeaderCard iterator:ownedLeaderCards) {
            if(iterator.isActive()) pointsFromLeaders += iterator.getVictoryPoints();
        }

        //Points from owned resources.
        for (Map.Entry<ResourceType, Integer> iterator: playerBoard.getInventoryManager().getInventory().entrySet()) {
            pointsFromResources += iterator.getValue();
        }

        victoryPoints += (pointsFromResources/5) + pointsFromLeaders + pointsFromFaithTrack + pointsFromProductions;

        return victoryPoints;
    }

    @Override
    public void visit(ActivateProductionAction action) {
        if(action.getActionSender().equals(playerName)
        && !playerBoard.getProductionBoard().getProductionSlots()[action.getSlot()].isSelected()){
            playerBoard.getProductionBoard().selectProductionSlot(action.getSlot());
        }
        //notify not available
    }

    @Override
    public void visit(ActivateExtraProductionAction action) {
        if(action.getActionSender().equals(playerName)
        && productionResourceValidator(action.getOutput())
        && extraProductionSlotValidator(action.getSlot())){
            playerBoard.getProductionBoard().selectLeaderProduction(action.getSlot(), action.getOutput());
        }
        //notify not available
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

            productionCardMarket.buyCard(action.getBoughtCard());
            action.getBoughtCard().placeCard(action.getDestinationSlot(), action.getBoughtCard(), playerBoard.getProductionBoard());

            ArrayList<ResourceTag> toBeRemoved = action.getBoughtCard().getRequirements();
            playerBoard.getInventoryManager().applyDiscount(toBeRemoved);

            playerBoard.getInventoryManager().setToBeRemoved(toBeRemoved);
            playerState.performedExclusiveAction();
            playerBoard.increaseBoughCardsCount();
        }
    }

    @Override
    public void visit(ChangeMarbleAction action) {
        if(action.getActionSender().equals(playerName)
        && bufferIndexValidator(action.getIndex())
        && colorExchangeValidator(action.getColor())){
            playerBoard.getInventoryManager().customExchange(action.getIndex(),action.getColor());

            playerState
                .setCanDeposit(playerBoard
                    .getInventoryManager()
                    .getBuffer()
                    .stream()
                    .anyMatch(MaterialResource -> MaterialResource.getResourceType().equals(UNDEFINED)));
        }
        //notify
    }

    @Override
    public void visit(DepositAction action) {
        if(action.getActionSender().equals(playerName)
           && (playerBoard.getInventoryManager().getBuffer().size() > action.getIndex())){
            try {
                playerBoard.getInventoryManager().addResourceToWarehouse(action.getIndex());

            } catch (DiscardResourceException exception) {
                notifyObserver(new DiscardedResourceMessage());
                playerBoard.getInventoryManager().removeFromBuffer(action.getIndex());
                //notify game of penalty
            }
        }

    }

    @Override
    public void visit(DiscardLeaderCardAction action) {
        if(action.getActionSender().equals(playerName)
        && !playerState.getGetHasPlacedLeaders()
        && (ownedLeaderCards.size() > action.getLeaderToDiscard())){

            if(playerState.isSetUpPhase()){
                setupLeaderCard(action.getLeaderToDiscard());
            }
            else{
                if(!ownedLeaderCards.get(action.getLeaderToDiscard()).isActive()) {
                    discardLeaderCard(action.getLeaderToDiscard());
                    playerBoard.getFaithTrack().increaseFaithMarker();
                    playerState.placedLeader();
                }
            }
        }
    }

    @Override
    public void visit(EndTurnAction action) {
        if(action.getActionSender().equals(playerName)
        && playerState.hasPerformedExclusiveAction()){
            playerState.endTurnReset();
        }
    }

    @Override
    public void visit(ExecuteProductionAction action) {
        if(action.getActionSender().equals(playerName)
        && finalProductionValidator()){
            playerBoard.getProductionBoard().executeProduction(playerBoard);
            playerBoard.getInventoryManager().addResourceToStrongbox();
            playerState.performedExclusiveAction();
        }
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
        if(action.getActionSender().equals(playerName)
        && !playerState.getGetHasPlacedLeaders()
        && leaderValidator(action.getLeaderToActivate())){
            ownedLeaderCards.get(action.getLeaderToActivate()).setActive(playerBoard);
            playerState.placedLeader();
            notifyObserver(new LeaderCardMessage(ownedLeaderCards));
        }
    }

    @Override
    public void visit(RemoveResourcesAction action) {
        if(action.getActionSender().equals(playerName)
        && (action.getSource().equals("STRONGBOX") || action.getSource().equals("WAREHOUSE"))){
            if(action.getSource().equals("WAREHOUSE")){
                try {
                    playerBoard.getInventoryManager().removeFromWarehouse(action.getToBeRemoved());
                } catch (CannotRemoveResourceException e) {
                    try {
                        playerBoard.getInventoryManager().removeFromStrongbox(new ResourceTag(e.getType(), e.getQuantity()));
                    } catch (CannotRemoveResourceException cannotRemoveResourceException) {
                        cannotRemoveResourceException.printStackTrace();
                    }
                }
            }
            else{
                try {
                    playerBoard.getInventoryManager().removeFromStrongbox(action.getToBeRemoved());
                } catch (CannotRemoveResourceException e) {
                    try {
                        playerBoard.getInventoryManager().removeFromWarehouse(new ResourceTag(e.getType(), e.getQuantity()));
                    } catch (CannotRemoveResourceException cannotRemoveResourceException) {
                        cannotRemoveResourceException.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void visit(LorenzoAction action) {

    }

    @Override
    public void visit(RearrangeInventoryAction action) {

    }


    private boolean productionResourceValidator(ResourceType wantedType){
        return !wantedType.equals(ResourceType.FAITH) && !wantedType.equals(ResourceType.UNDEFINED);
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

    private boolean leaderValidator (int index){
        if(index > ownedLeaderCards.size()) return false;
        else{
            if(ownedLeaderCards.get(index).isActive()) return false;
            else return leaderRequirementsValidator(ownedLeaderCards.get(index));
        }
    }

    private boolean leaderRequirementsValidator(LeaderCard leader){
        if(leader.getEffectType().equals(EffectType.EXTRA_INVENTORY)){
            Map<ResourceType, Integer> actualInventory = playerBoard.getInventoryManager().getInventory();

            for (ResourceTag requirement : leader.getRequirementsResource()) {
                if(requirement.getQuantity() > actualInventory.get(requirement.getType()))
                    return false;
            }
        }
        else{
            for(DevelopmentTag iterator : leader.getRequirementsDevCards()) {

                if(iterator.getQuantity() > playerBoard.getProductionBoard().getCardsInventory().get(iterator.getColor())[iterator.getLevel().ordinal()])
                    return false;
            }
        }
        return true;

    }

    private boolean extraProductionSlotValidator(int slot) {
        if(slot > playerBoard.getProductionBoard().getLeaderProductions().size()) return false;
        else{
            return !playerBoard.getProductionBoard().getLeaderProductions().get(slot).getSelected();
        }
    }

    private boolean colorExchangeValidator(ResourceType color){
         return playerBoard.getInventoryManager().getExchange().contains(color);
    }

    private boolean bufferIndexValidator(int index){
        return playerBoard.getInventoryManager().getBuffer().get(index).getResourceType().equals(ResourceType.UNDEFINED);
    }

    private boolean finalProductionValidator() {
        return playerBoard.getProductionBoard().validateFinalProduction(playerBoard.getInventoryManager());
    }

    public void getPeek() {

    }

    /**
     * @return reduced version of owned leader cards.
     */
    public List<EffectType> reduceLeaders() {
        List<EffectType> e = new ArrayList<>();

        for(LeaderCard l : ownedLeaderCards) {
            if(l.isActive()) {
                e.add(l.getEffectType());
            }
        }
        return e;
    }

    /**
     * @return position on the faith track
     */
    public int getFaithPosition() {
        return playerBoard.getFaithTrack().getFaithMarker();
    }

    /**
     * @return inventory from inventory manager
     */
    public Map<ResourceType, Integer> getInventory() {
        return playerBoard.getInventoryManager().getInventory();
    }

    /**
     * Increments the value of the faith marker.
     */
    public void moveFaith() {
        playerBoard.getFaithTrack().increaseFaithMarker();
    }

    /**
     * Method to assign a random material resource to a player if he gets disconnected during the setup phase.
     */
    public void assignRandomResource() {
        try {
            playerBoard
                .getInventoryManager()
                .getWarehouse()
                .addResource(ResourceType.randomizedMaterialResource());
        } catch (DiscardResourceException ignored) {
            //shouldn't happen.
        }
    }
}
