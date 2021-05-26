package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.eventHandlers.ViewObservable;
import it.polimi.ingsw.network.views.IView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GUI extends ViewObservable implements IView {


    public GUI(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
    }


    @Override
    public void askNickname() {

    }

    @Override
    public void askPlayerNumber() {

    }

    @Override
    public void showLoginOutput(boolean connectionSuccess, boolean nicknameAccepted, boolean reconnected) {

    }

    @Override
    public void showGenericString(String genericMessage) {

    }

    @Override
    public void showInvalidAction(String errorMessage) {

    }

    @Override
    public void askReplacementResource(ResourceType r1, ResourceType r2) {

    }

    @Override
    public void askToDiscard() throws ExecutionException {

    }

    @Override
    public void showLeaderCards(List<LeaderCard> cards) {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void currentTurn(String message) {

    }

    @Override
    public void turnEnded(String message) {

    }

    @Override
    public void askSetupResource(int number) throws ExecutionException {

    }

    @Override
    public void showMatchInfo(List<String> playingNames) {

    }

    @Override
    public void showWinMatch(String winner) {

    }

    @Override
    public void printResourceMarket(ResourceType[][] resourceMarket, ResourceType extraMarble) {

    }

    @Override
    public void printAvailableCards(List<ProductionCard> available) {

    }

    @Override
    public void printFaithTrack(FaithTrack faithTrack) {

    }

    @Override
    public void printLorenzoToken(String lorenzoTokenReduced) {

    }

    @Override
    public void printLeaders(List<LeaderCard> leaderCards) {

    }

    @Override
    public void printBuffer(ArrayList<ResourceType> buffer) {

    }

    @Override
    public void printStrongbox(HashMap<ResourceType, Integer> strongbox) {

    }

    @Override
    public void printWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras) {

    }

    @Override
    public void printProductionBoard(HashMap<Integer, ProductionCard> productionBoard) {

    }

    @Override
    public void printFinalProduction(HashMap<ResourceType, Integer> input, HashMap<ResourceType, Integer> output) {

    }
}
