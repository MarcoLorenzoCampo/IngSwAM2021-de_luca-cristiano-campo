package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.eventHandlers.ViewObservable;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.utilities.NetworkInfoValidator;
import it.polimi.ingsw.network.views.IView;
import it.polimi.ingsw.network.views.cli.LightweightModel;
import it.polimi.ingsw.network.views.cli.UsefulStrings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class GUI extends ViewObservable implements IView, ActionListener {

    LightweightModel lightweightModel;
    private boolean isOnline;

    private SetupPopUp setupPopUp;

    private PlayerNumberPopUp playerNumberPopUp;
    private OnlineLoginPopUp onlineLoginPopUp;
    private NicknamePopUp nicknamePopUp;
    private MessagePopUp messagePopUp;

    public GUI(boolean isOnline){
        lightweightModel = new LightweightModel();
        onlineLoginPopUp = new OnlineLoginPopUp(this);
        nicknamePopUp = new NicknamePopUp(this);
        playerNumberPopUp = new PlayerNumberPopUp(this);
        messagePopUp = new MessagePopUp(" ");

        this.isOnline = isOnline;
        startGUI();
    }

    private void startGUI() {
        if(isOnline){
            onlineLoginPopUp.setVisible(true);
        } else {
            askNickname();
        }
    }

    @Override
    public void getPeek(String name, int faithPosition, Map<ResourceType, Integer> inventory, List<EffectType> cards) {

    }

    /**
     * Prints a reduced version of a player's inventory.
     */
    @Override
    public void printInventory(Map<ResourceType, Integer> inventory) {

    }

    @Override
    public void askNickname() {
        nicknamePopUp.setVisible(true);
    }

    @Override
    public void askPlayerNumber() {
        if(!isOnline) {
            notifyObserver(o -> o.onUpdateNumberOfPlayers(1));

        } else {
            playerNumberPopUp.setVisible(true);
        }
    }

    @Override
    public void showLoginOutput(boolean connectionSuccess, boolean nicknameAccepted, boolean reconnected) {
        if(connectionSuccess && nicknameAccepted && !reconnected) {
            messagePopUp.changeMessage("CONNECTED");

        } else if(connectionSuccess && !nicknameAccepted && !reconnected) {
            messagePopUp.changeMessage("CHOOSE ANOTHER NICKNAME");

            askNickname();

        } else if(reconnected) {
            messagePopUp.changeMessage("YOU HAVE BEEN RECONNECTED");

        } else {
            messagePopUp.changeMessage("SORRY YOU CAN'T JOIN THE GAME");
            System.exit(1);
        }

    }

    @Override
    public void showGenericString(String genericMessage) {
        // {
        //    Thread.sleep(2000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        messagePopUp.changeMessage(genericMessage);
        System.out.println(genericMessage);
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
        if(number!=0) {
            ResourcesPopUp resourcesPopUp = new ResourcesPopUp(this, number);
            resourcesPopUp.setVisible(true);
            resourcesPopUp.getSUBMITButton().addActionListener(e ->{
                LinkedList<ResourceType> send = new LinkedList<>();
                if(!resourcesPopUp.getTextField1().getText().isEmpty() && resourcesPopUp.getNumber()==2){
                    send.add(ResourceType.valueOf(resourcesPopUp.getTextField1().getText()));
                    send.add(ResourceType.valueOf(resourcesPopUp.getTextField2().getText()));
                    System.out.println(resourcesPopUp.getTextField1().getText()+" "+resourcesPopUp.getTextField2().getText());
                    notifyObserver(o -> o.onUpdateSetupResource(send));
                    resourcesPopUp.dispose();
                }
                else if(!resourcesPopUp.getTextField1().getText().isEmpty() && resourcesPopUp.getNumber()==1){
                    send.add(ResourceType.valueOf(resourcesPopUp.getTextField1().getText()));
                    System.out.println(resourcesPopUp.getTextField1().getText()+" "+resourcesPopUp.getTextField2().getText());
                    notifyObserver(o -> o.onUpdateSetupResource(send));
                    resourcesPopUp.dispose();
                }
            });
        }
        else {
            messagePopUp.changeMessage("The first player doesn't get to pick any resource!");
            notifyObserver(o -> o.onUpdateSetupResource(new LinkedList<ResourceType>()));
        }
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
    public void printStrongbox(Map<ResourceType, Integer> strongbox) {

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(onlineLoginPopUp.getSubmit())){
            int socket = Integer.parseInt(onlineLoginPopUp.getSocket_input().getText());
            String ip = onlineLoginPopUp.getIp_input().getText();
            if(NetworkInfoValidator.isIPAddressValid(ip) && NetworkInfoValidator.isPortValid(socket)){
                onlineLoginPopUp.dispose();
                notifyObserver(o -> o.onServerInfoUpdate(socket, ip));
            } else {
                JFrame error = new JFrame();
                error.setSize(400,400);
                JLabel error_message = new JLabel("INPUT NON CORRETTO, RIPROVARE");
                error_message.setVerticalAlignment(SwingConstants.CENTER);
                error_message.setHorizontalAlignment(SwingConstants.CENTER);
                error.add(error_message);
                error.setVisible(true);
            }
        }

        if(e.getSource().equals(nicknamePopUp.getSubmit())){
            String nickname = nicknamePopUp.getUsername().getText();
            notifyObserver(o -> o.onUpdateNickname(nickname));
            nicknamePopUp.dispose();
        }

        if(e.getSource().equals(playerNumberPopUp.getOne())){
            notifyObserver(o -> o.onUpdateNumberOfPlayers(1));
            playerNumberPopUp.dispose();
        }

        if(e.getSource().equals(playerNumberPopUp.getTwo())){
            notifyObserver(o -> o.onUpdateNumberOfPlayers(2));
            playerNumberPopUp.dispose();
        }

        if(e.getSource().equals(playerNumberPopUp.getThree())){
            notifyObserver(o -> o.onUpdateNumberOfPlayers(3));
            playerNumberPopUp.dispose();
        }

        if(e.getSource().equals(playerNumberPopUp.getFour())){
            notifyObserver(o -> o.onUpdateNumberOfPlayers(4));
            playerNumberPopUp.dispose();
        }

    }
}
