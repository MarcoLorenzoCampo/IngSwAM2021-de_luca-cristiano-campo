package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.eventHandlers.ViewObservable;
import it.polimi.ingsw.network.utilities.NetworkInfoValidator;
import it.polimi.ingsw.network.views.IView;
import it.polimi.ingsw.network.views.cli.LightweightModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GUI extends ViewObservable implements IView {

    private final LightweightModel lightweightModel;
    private boolean isOnline;
    private final JFrame setupFrame;
    private ArrayList<JPanel> setup;
    private ArrayList<JPanel> leaderPanels;


    private final JFrame mainframe;

    private final FaithTrackPanel faithTrackPanel;
    private final ProductionBoardPanel productionBoardPanel;
    private final WarehousePanel warehousePanel;
    private final StrongBoxPanel strongBoxPanel;
    //private final BufferPanel bufferPanel;
    //private final FinalProductionPanel finalProductionPanel;

    private final CardMarketPanel cardMarketPanel;
    private final ResourceMarketPanel resourceMarketPanel;

    private MessagePopUp messagePopUp;

    public GUI(boolean isOnline){
        lightweightModel = new LightweightModel();
        messagePopUp = new MessagePopUp(" ");
        setupFrame = new JFrame();
        setup = new ArrayList<>();
        leaderPanels = new ArrayList<>();
        this.isOnline = isOnline;

        initializeSetupFrame();
        initializeSetupPanels();

        faithTrackPanel = new FaithTrackPanel();
        productionBoardPanel = new ProductionBoardPanel();
        warehousePanel = new WarehousePanel();
        strongBoxPanel = new StrongBoxPanel();

        cardMarketPanel = new CardMarketPanel();
        resourceMarketPanel = new ResourceMarketPanel();

        mainframe = new JFrame();
        initializeMainFrame();
        startGUI();
    }

    private void initializeMainFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainframe.setSize(screenSize.width,screenSize.height);



    }

    private void initializeSetupPanels() {
        setup.add(onlineLoginPopUp());
        setup.add(nicknamePopUp());
        setup.add(playerNumberPopUp());
    }

    private JPanel resourcesPopUp(int quantity) {
        ResourcesPopUp resourcesPopUp = new ResourcesPopUp(quantity);
        resourcesPopUp.getSUBMITButton().addActionListener(e ->{
            LinkedList<ResourceType> send = new LinkedList<>();
            if(!resourcesPopUp.getTextField1().getText().isEmpty() && resourcesPopUp.getNumber()==2){
                send.add(ResourceType.valueOf(resourcesPopUp.getTextField1().getText()));
                send.add(ResourceType.valueOf(resourcesPopUp.getTextField2().getText()));
                System.out.println(resourcesPopUp.getTextField1().getText()+" "+resourcesPopUp.getTextField2().getText());
                notifyObserver(o -> o.onUpdateSetupResource(send));
                setupFrame.dispose();
            }
            else if(!resourcesPopUp.getTextField1().getText().isEmpty() && resourcesPopUp.getNumber()==1){
                send.add(ResourceType.valueOf(resourcesPopUp.getTextField1().getText()));
                System.out.println(resourcesPopUp.getTextField1().getText()+" "+resourcesPopUp.getTextField2().getText());
                notifyObserver(o -> o.onUpdateSetupResource(send));
                setupFrame.dispose();

            }
        });
        return resourcesPopUp.getMainPanel();
    }

    private JPanel playerNumberPopUp() {
        JPanel playerNumberPopUp = new JPanel();
        playerNumberPopUp.setLayout(new BorderLayout(5, 20));

        JLabel numberOfPlayers = new JLabel("Select the number of players");
        numberOfPlayers.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfPlayers.setVerticalAlignment(SwingConstants.TOP);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setSize(420,120);

        JButton one = new JButton("1");
        one.setSize(105,60);
        one.setFocusable(false);
        one.addActionListener(e -> {
            notifyObserver(o -> o.onUpdateNumberOfPlayers(1));
            setupFrame.dispose();
        });

        JButton two = new JButton("2");
        two.setSize(105,60);
        two.setFocusable(false);
        two.addActionListener(e -> {
            notifyObserver(o -> o.onUpdateNumberOfPlayers(2));
            setupFrame.dispose();
        });

        JButton three = new JButton("3");
        three.setSize(105,60);
        three.setFocusable(false);
        three.addActionListener(e -> {
            notifyObserver(o -> o.onUpdateNumberOfPlayers(3));
            setupFrame.dispose();
        });

        JButton four = new JButton("4");
        four.setSize(105,60);
        four.setFocusable(false);
        four.addActionListener(e -> {
            notifyObserver(o -> o.onUpdateNumberOfPlayers(4));
            setupFrame.dispose();
        });


        buttonPanel.add(one);
        buttonPanel.add(two);
        buttonPanel.add(three);
        buttonPanel.add(four);

        playerNumberPopUp.add(numberOfPlayers);
        playerNumberPopUp.add(buttonPanel, BorderLayout.SOUTH);

        return playerNumberPopUp;
    }

    private JPanel onlineLoginPopUp() {
        JPanel onlineLoginPopUp = new JPanel();
        onlineLoginPopUp.setLayout(new BorderLayout(5, 20));

        JPanel title = new JPanel();

        JPanel central = new JPanel();
        central.setLayout(new BorderLayout(5, 30));
        JPanel upperCentral = new JPanel();
        JPanel lowerCentral = new JPanel();
        JPanel bottom = new JPanel();


        JLabel title_1 = new JLabel("<html>INSERT SOCKET PORT NUMBER AND IP ADDRESS<br>DEFAULT IS: <br><br>SOCKET = 2200<BR>IP = 0.0.0.0</html>");
        title.setLayout(new FlowLayout());
        title.add(title_1);


        upperCentral.setLayout(new FlowLayout());
        JLabel socketPort = new JLabel("Socket Port :");
        JTextField socket_input = new JTextField();
        socket_input.setPreferredSize(new Dimension(200, 30));
        upperCentral.add(socketPort);
        upperCentral.add(socket_input);


        lowerCentral.setLayout(new FlowLayout());
        JLabel ipAddress = new JLabel("IP address :");
        JTextField ip_input = new JTextField();
        ip_input.setPreferredSize(new Dimension(200, 30));
        lowerCentral.add(ipAddress);
        lowerCentral.add(ip_input);

        bottom.setLayout(new FlowLayout());
        JButton submit = new JButton();
        submit.setText("SUBMIT");
        submit.setFocusable(false);
        submit.setBounds(160,200,100,30);
        submit.addActionListener(e -> {
            int socket = Integer.parseInt(socket_input.getText());
            String ip = ip_input.getText();
            if(NetworkInfoValidator.isIPAddressValid(ip) && NetworkInfoValidator.isPortValid(socket)){
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
        });
        bottom.add(submit);

        socket_input.setText("2200");
        ip_input.setText("0.0.0.0");
        central.add(upperCentral,BorderLayout.NORTH);
        central.add(lowerCentral,BorderLayout.CENTER);
        onlineLoginPopUp.add(title, BorderLayout.NORTH);
        onlineLoginPopUp.add(central, BorderLayout.CENTER);
        onlineLoginPopUp.add(bottom, BorderLayout.SOUTH);

        return onlineLoginPopUp;
    }

    private JPanel nicknamePopUp() {
        JPanel nicknamePopUp = new JPanel();
        nicknamePopUp.setLayout(new BorderLayout(5, 20));

        JPanel north = new JPanel();
        north.setLayout(new FlowLayout());

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());

        JPanel south = new JPanel();
        south.setLayout(new FlowLayout());


        JLabel title = new JLabel("<html>CHOOSE A USERNAME</html>");
        JLabel username = new JLabel("Username :");
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(200, 30));

        JButton submit = new JButton("SUBMIT");
        submit.setFocusable(false);
        submit.addActionListener(e -> {
            notifyObserver(o -> o.onUpdateNickname(input.getText()));
            setupFrame.dispose();
        });

        north.add(title);
        center.add(username);
        center.add(input);
        south.add(submit);

        nicknamePopUp.add(north, BorderLayout.NORTH);
        nicknamePopUp.add(center, BorderLayout.CENTER);
        nicknamePopUp.add(south, BorderLayout.SOUTH);
        return nicknamePopUp;
    }

    private void initializeSetupFrame() {
        setupFrame.setSize(800,800);
        setupFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setupFrame.setTitle("SETUP");
    }

    private void startGUI() {
        if(isOnline){
            setupFrame.setContentPane(onlineLoginPopUp());
            setupFrame.pack();
            setupFrame.revalidate();
            setupFrame.repaint();
            setupFrame.getContentPane().setVisible(true);
            setupFrame.setVisible(true);
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
        setupFrame.getContentPane().removeAll();
        setupFrame.setContentPane(setup.get(1));
        setupFrame.validate();
        setupFrame.repaint();
    }

    @Override
    public void askPlayerNumber() {
        if(!isOnline) {
            notifyObserver(o -> o.onUpdateNumberOfPlayers(1));

        } else {
            setupFrame.getContentPane().removeAll();
            setupFrame.setContentPane(setup.get(2));
            setupFrame.revalidate();
            setupFrame.repaint();
            setupFrame.setVisible(true);
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
        setupFrame.getContentPane().removeAll();
        setupFrame.setContentPane(leaderPanels.get(0));
        setupFrame.revalidate();
        setupFrame.repaint();
        setupFrame.setVisible(true);
    }

    @Override
    public void showLeaderCards(List<LeaderCard> cards) {
        if(leaderPanels.isEmpty()){
            JPanel setupLeaderPopUp = setupLeaderPopUp(cards);
            leaderPanels.add(setupLeaderPopUp);
        }
        else{
            //leaderPanels.get(1) = normal show leaders;
        }
    }

    private JPanel setupLeaderPopUp(List<LeaderCard> available) {
       SetupLeaderPopUp setupLeaderPopUp = new SetupLeaderPopUp(available);
       setupLeaderPopUp.getSubmit_button().addActionListener(e->{
           ArrayList<Integer> selected = new ArrayList<>();
           for (int i = 0; i < setupLeaderPopUp.getCheckBoxes().length; i++) {
               if(setupLeaderPopUp.getCheckBoxes()[i].isSelected()) selected.add(i);
           }
           notifyObserver(o -> o.onUpdateSetupLeaders(selected.get(0), selected.get(1)));
           setupFrame.dispose();
       });
        return setupLeaderPopUp;
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
            setupFrame.getContentPane().removeAll();
            setupFrame.setContentPane(resourcesPopUp(number));
            setupFrame.revalidate();
            setupFrame.repaint();
            setupFrame.setVisible(true);
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
        resourceMarketPanel.updateMarket(resourceMarket, extraMarble);
    }

    @Override
    public void printAvailableCards(List<ProductionCard> available) {
        cardMarketPanel.updateCardMarketPanel(available);
    }

    @Override
    public void printFaithTrack(FaithTrack faithTrack) {
        faithTrackPanel.updateFaithTrackPanel(faithTrack);
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
        strongBoxPanel.updateStrongboxPanel(strongbox);
    }

    @Override
    public void printWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras) {
        warehousePanel.updateWarehousePanel(shelves, extras);
    }

    @Override
    public void printProductionBoard(HashMap<Integer, ProductionCard> productionBoard) {

        //DA MODIFICARE PER PRENDERE I LEADER
        productionBoardPanel.updateProductionBoardPanel(productionBoard, new ArrayList<>());
    }

    @Override
    public void printFinalProduction(HashMap<ResourceType, Integer> input, HashMap<ResourceType, Integer> output) {

    }

}
