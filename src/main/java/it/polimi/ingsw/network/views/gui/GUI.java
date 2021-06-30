package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.eventHandlers.ViewObservable;
import it.polimi.ingsw.network.eventHandlers.ViewObserver;
import it.polimi.ingsw.network.utilities.NetworkInfoValidator;
import it.polimi.ingsw.network.views.IView;



import javax.swing.*;
import java.awt.*;

import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GUI extends ViewObservable implements IView {


    private final boolean isOnline;
    private final JFrame setupFrame;
    private StartPanel startPanel;
    private final ArrayList<JPanel> setup;
    private final ArrayList<JPanel> leaderPanels;


    private final JFrame mainframe;

    private final FaithTrackPanel faithTrackPanel;
    private final ProductionBoardPanel productionBoardPanel;
    private final WarehousePanel warehousePanel;
    private final StrongBoxPanel strongBoxPanel;
    private final AvailableLeaderPanel availableLeaderPanel;
    private final LeaderRecapPanel leaderRecapPanel;
    private final JButton[] actionButtons;
    private final JFrame buttonsPopUp;
    private final BufferPanel bufferPanel;
    private final ExchangeResourcePanel[] exchangeResourcePanels;
    private final JFrame exchangePopUp;
    private final BaseProductionPanel baseProductionPanel;
    private  final ExtraProductionPanel[] extraProductionPanels;
    private final JFrame productionPopUp;
    private final FinalProductionPanel finalProductionPanel;
    private final EnemyPlayerPopUp enemyPlayerPopUp;
    private final LorenzoTokenPanel lorenzoTokenPanel;



    private final CardMarketPanel cardMarketPanel;
    private final JFrame buyCardPopUp;
    private final BuyCardPanel[]  buyCardPanels;
    private final ResourceMarketPanel resourceMarketPanel;

    private final MessagePopUp messagePopUp;

    public GUI(boolean isOnline){
        messagePopUp = new MessagePopUp(" ");
        setupFrame = new JFrame();
        startPanel = new StartPanel();
        setup = new ArrayList<>();
        leaderPanels = new ArrayList<>();
        this.isOnline = isOnline;
        initializeSetupFrame();
        initializeSetupPanels();
        enemyPlayerPopUp = new EnemyPlayerPopUp();
        lorenzoTokenPanel = new LorenzoTokenPanel();

        actionButtons = new JButton[5];
        buttonsPopUp = new JFrame();
        setActionListeners();

        faithTrackPanel = new FaithTrackPanel();

        productionPopUp = new JFrame();
        productionBoardPanel = new ProductionBoardPanel();
        baseProductionPanel = new BaseProductionPanel();
        extraProductionPanels = new ExtraProductionPanel[2];
        extraProductionPanels[0] = new ExtraProductionPanel();
        extraProductionPanels[1] = new ExtraProductionPanel();
        setProductionButtons();

        warehousePanel = new WarehousePanel();
        setupSourceWarehouse();

        strongBoxPanel = new StrongBoxPanel();
        setupSourceStrongbox();

        availableLeaderPanel = new AvailableLeaderPanel();
        leaderRecapPanel = new LeaderRecapPanel();
        setupLeaderButtons();

        finalProductionPanel = new FinalProductionPanel();
        bufferPanel= new BufferPanel();
        exchangeResourcePanels = new ExchangeResourcePanel[4];
        exchangeResourcePanels[0] = new ExchangeResourcePanel();
        exchangeResourcePanels[1] = new ExchangeResourcePanel();
        exchangeResourcePanels[2] = new ExchangeResourcePanel();
        exchangeResourcePanels[3] = new ExchangeResourcePanel();
        exchangePopUp = new JFrame();
        setBufferActionListener();


        buyCardPopUp = new JFrame();
        buyCardPopUp.setSize(500,500);
        cardMarketPanel = new CardMarketPanel();
        buyCardPanels = new BuyCardPanel[12];
        for (int i = 0; i < 12; i++) {
            buyCardPanels[i] = new BuyCardPanel(i);
        }
        setCardMarketButtons();
        setBuyCardButtons();


        resourceMarketPanel = new ResourceMarketPanel();
        setArrowsActionListener();


        mainframe = new JFrame();
        initializeMainFrame();
        startGUI();
    }

    private void setupLeaderButtons() {
        availableLeaderPanel.getButtons().get(0).addActionListener(e -> notifyObserver(o -> o.onUpdateActivateLeader(0)));
        availableLeaderPanel.getButtons().get(1).addActionListener(e -> notifyObserver(o -> o.onUpdateActivateLeader(1)));
        availableLeaderPanel.getButtons().get(2).addActionListener(e -> notifyObserver(o -> o.onUpdateDiscardLeader(0)));
        availableLeaderPanel.getButtons().get(3).addActionListener(e -> notifyObserver(o -> o.onUpdateDiscardLeader(1)));
    }

    private void setProductionButtons() {
        baseProductionPanel.getSubmit().addActionListener(e -> {
            ArrayList<ResourceType> production = baseProductionPanel.getProduction();
            notifyObserver(o -> o.onUpdateBaseActivation(production.get(0), production.get(1), production.get(2)));
            baseProductionPanel.resetProduction();
            productionPopUp.dispose();
        });

        productionBoardPanel.getButtons()[0].addActionListener(e -> {
            productionPopUp.setContentPane(baseProductionPanel);
            productionPopUp.revalidate();
            productionPopUp.repaint();
            productionPopUp.setVisible(true);
        });
        productionBoardPanel.getButtons()[1].addActionListener(e -> notifyObserver(o -> o.onUpdateActivateProductionCard(0)));
        productionBoardPanel.getButtons()[2].addActionListener(e -> notifyObserver(o -> o.onUpdateActivateProductionCard(1)));
        productionBoardPanel.getButtons()[3].addActionListener(e -> notifyObserver(o -> o.onUpdateActivateProductionCard(2)));

        productionBoardPanel.getButtons()[4].addActionListener(e -> {
            extraProductionPanels[0].updateExtraProductionPanel(productionBoardPanel.getExtraLeader(0));
            productionPopUp.setContentPane(extraProductionPanels[0]);
            productionPopUp.pack();
            productionPopUp.revalidate();
            productionPopUp.repaint();
            productionPopUp.setSize(600, 275);
            productionPopUp.setVisible(true);
        });
        productionBoardPanel.getButtons()[5].addActionListener(e -> {
            extraProductionPanels[1].updateExtraProductionPanel(productionBoardPanel.getExtraLeader(1));
            productionPopUp.setContentPane(extraProductionPanels[1]);
            productionPopUp.pack();
            productionPopUp.revalidate();
            productionPopUp.repaint();
            productionPopUp.setSize(600, 275);
            productionPopUp.setVisible(true);
        });
        productionBoardPanel.getButtons()[6].addActionListener(e -> notifyObserver(ViewObserver::onUpdateExecuteProduction));


        extraProductionPanels[0].getSubmit().addActionListener(e -> {
            notifyObserver(o -> o.onUpdateActivateExtraProduction(0,extraProductionPanels[0].getChosen()));
            productionPopUp.dispose();
            extraProductionPanels[0].clearSelection();
        });
        extraProductionPanels[1].getSubmit().addActionListener(e -> {
            notifyObserver(o -> o.onUpdateActivateExtraProduction(1, extraProductionPanels[1].getChosen()));
            productionPopUp.dispose();
            extraProductionPanels[1].clearSelection();
        });
    }

    private void setupSourceWarehouse() {
        warehousePanel.getSource_warehouse().addActionListener(e -> notifyObserver(ViewObserver::onUpdateSourceWarehouse));
    }

    private void setupSourceStrongbox() {
        strongBoxPanel.getSource_strongbox().addActionListener(e -> notifyObserver(ViewObserver::onUpdateSourceStrongBox));
    }

    private void setCardMarketButtons() {
        ArrayList<JButton> cards = cardMarketPanel.getButtons();
        cards.get(0).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[0]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);
        });
        cards.get(1).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[1]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);
        });
        cards.get(2).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[2]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(3).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[3]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(4).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[4]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(5).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[5]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(6).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[6]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(7).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[7]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(8).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[8]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(9).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[9]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(10).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[10]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
        cards.get(11).addActionListener(e -> {
            buyCardPopUp.setContentPane(buyCardPanels[11]);
            buyCardPopUp.revalidate();
            buyCardPopUp.setVisible(true);

        });
    }

    private void setBuyCardButtons() {
        for (BuyCardPanel iterator: buyCardPanels) {
            JButton[] slots = iterator.getSlots();
            for (int i = 0; i < slots.length; i++) {
                int finalI = i;
                slots[i].addActionListener(e -> {
                    notifyObserver(o -> o.onUpdateBuyCard(iterator.getSelectedCard(), finalI));
                    buyCardPopUp.dispose();
                });
            }
        }
    }

    private void setBufferActionListener() {
        JButton[] deposit = bufferPanel.getDeposit_buttons();
        deposit[0].addActionListener(e -> notifyObserver(o -> o.onUpdateDeposit(0)));
        deposit[1].addActionListener(e -> notifyObserver(o -> o.onUpdateDeposit(1)));
        deposit[2].addActionListener(e -> notifyObserver(o -> o.onUpdateDeposit(2)));
        deposit[3].addActionListener(e -> notifyObserver(o -> o.onUpdateDeposit(3)));

        JButton[] exchange = bufferPanel.getChange_buttons();
        exchange[0].addActionListener(e -> {
            exchangePopUp.setContentPane(exchangeResourcePanels[0]);
            exchangePopUp.revalidate();
            exchangePopUp.setVisible(true);
        });
        exchange[1].addActionListener(e -> {
            exchangePopUp.setContentPane(exchangeResourcePanels[1]);
            exchangePopUp.revalidate();
            exchangePopUp.setVisible(true);
        });
        exchange[2].addActionListener(e -> {
            exchangePopUp.setContentPane(exchangeResourcePanels[2]);
            exchangePopUp.revalidate();
            exchangePopUp.setVisible(true);
        });
        exchange[3].addActionListener(e -> {
            exchangePopUp.setContentPane(exchangeResourcePanels[3]);
            exchangePopUp.revalidate();
            exchangePopUp.setVisible(true);
        });

        exchangeResourcePanels[0].getResources()[0].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.COIN,0)));
        exchangeResourcePanels[0].getResources()[1].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.SHIELD,0)));
        exchangeResourcePanels[0].getResources()[2].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.SERVANT,0)));
        exchangeResourcePanels[0].getResources()[3].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.STONE,0)));

        exchangeResourcePanels[1].getResources()[0].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.COIN,1)));
        exchangeResourcePanels[1].getResources()[1].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.SHIELD,1)));
        exchangeResourcePanels[1].getResources()[2].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.SERVANT,1)));
        exchangeResourcePanels[1].getResources()[3].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.STONE,1)));

        exchangeResourcePanels[2].getResources()[0].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.COIN,2)));
        exchangeResourcePanels[2].getResources()[1].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.SHIELD,2)));
        exchangeResourcePanels[2].getResources()[2].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.SERVANT,2)));
        exchangeResourcePanels[2].getResources()[3].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.STONE,2)));

        exchangeResourcePanels[3].getResources()[0].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.COIN,3)));
        exchangeResourcePanels[3].getResources()[1].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.SHIELD,3)));
        exchangeResourcePanels[3].getResources()[2].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.SERVANT,3)));
        exchangeResourcePanels[3].getResources()[3].addActionListener(e -> notifyObserver(o -> o.onUpdateExchangeResource(ResourceType.STONE,3)));
    }

    private void setActionListeners() {

        for (int i = 0; i < 5; i++) {
            actionButtons[i]= new JButton();
            actionButtons[i].setFocusable(false);
        }
        actionButtons[0].setText("Card Market");
        actionButtons[0].addActionListener(e -> {
            buttonsPopUp.setContentPane(cardMarketPanel);
            buttonsPopUp.pack();
            buttonsPopUp.revalidate();
            buttonsPopUp.setSize(660,800);
            buttonsPopUp.setVisible(true);
        });

        actionButtons[1].setText("Resource Market");
        actionButtons[1].addActionListener(e -> {
            buttonsPopUp.setContentPane(resourceMarketPanel);
            buttonsPopUp.pack();
            buttonsPopUp.revalidate();
            buttonsPopUp.setSize(660,740);
            buttonsPopUp.setVisible(true);
        });

        actionButtons[2].setText("Enemies");
        actionButtons[2].addActionListener(e -> {
            buttonsPopUp.setContentPane(enemyPlayerPopUp);
            buttonsPopUp.pack();
            buttonsPopUp.revalidate();
            buttonsPopUp.setSize(600,800);
            buttonsPopUp.setVisible(true);
        });

        actionButtons[3].setText("Leaders");
        actionButtons[3].addActionListener(e -> {
            buttonsPopUp.setContentPane(availableLeaderPanel);
            buttonsPopUp.pack();
            buttonsPopUp.revalidate();
            buttonsPopUp.setSize(800,650);
            buttonsPopUp.setVisible(true);
        });

        actionButtons[4].setText("END TURN");
        actionButtons[4].addActionListener(e -> notifyObserver(ViewObserver::onUpdateEndTurn));
    }

    private void setArrowsActionListener(){

        JButton[] arrows = resourceMarketPanel.getArrows();
        arrows[0].addActionListener(e -> notifyObserver(o -> o.onUpdateGetResources(0)));
        arrows[1].addActionListener(e -> notifyObserver(o -> o.onUpdateGetResources(1)));
        arrows[2].addActionListener(e -> notifyObserver(o -> o.onUpdateGetResources(2)));
        arrows[3].addActionListener(e -> notifyObserver(o -> o.onUpdateGetResources(3)));
        arrows[4].addActionListener(e -> notifyObserver(o -> o.onUpdateGetResources(4)));
        arrows[5].addActionListener(e -> notifyObserver(o -> o.onUpdateGetResources(5)));
        arrows[6].addActionListener(e -> notifyObserver(o -> o.onUpdateGetResources(6)));

    }

    private void initializeMainFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainframe.setSize(screenSize.width,screenSize.height-20);
        mainframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);


        int width = screenSize.width/35;
        int height = (screenSize.height-20)/21;

        createWestPanel(width, height, mainframe);
        createEastPanel(width, height, mainframe);
        createMiddlePanel(width, height, mainframe);


       mainframe.repaint();
       mainframe.setVisible(true);


    }

    private void createMiddlePanel(int width, int height, JFrame mainframe) {
        JPanel middle = new JPanel();
        middle.setLayout(new BorderLayout());

        faithTrackPanel.setPreferredSize(new Dimension(width, 4*height));
        productionBoardPanel.setPreferredSize(new Dimension(width, 12*height));

        JPanel south = new JPanel();
        south.setPreferredSize(new Dimension(width, 5*height));
        south.setBackground(new Color(146, 123, 91));
        south.setLayout(new GridLayout(1,2));
        south.add(finalProductionPanel);
        south.add(bufferPanel);

        middle.add(faithTrackPanel, BorderLayout.NORTH);
        middle.add(productionBoardPanel, BorderLayout.CENTER);
        middle.add(south, BorderLayout.SOUTH);

        mainframe.add(middle);
    }

    private void createEastPanel(int width, int height, JFrame mainframe) {
        JPanel east = new JPanel();
        east.setPreferredSize(new Dimension(4*width, height));
        east.setBackground(new Color(146, 123, 91));
        east.setLayout(new BorderLayout());

        lorenzoTokenPanel.setPreferredSize(new Dimension(4*width, 4*height));

        JPanel east_center = new JPanel();
        east_center.setBackground(new Color(202,190,152));
        east_center.setPreferredSize(new Dimension(4*width,12*height));
        east_center.setLayout(new FlowLayout());
        for (JButton iterator: actionButtons) {
            east_center.add(iterator);
        }


        leaderRecapPanel.setPreferredSize(new Dimension(4*width, 5*height));

        JPanel messages = (JPanel) messagePopUp.getContentPane();
        messages.setOpaque(false);
        messages.setPreferredSize(new Dimension(4*width, 5*height));
        east.add(lorenzoTokenPanel, BorderLayout.NORTH);
        east.add(leaderRecapPanel, BorderLayout.SOUTH);
        east.add(east_center, BorderLayout.CENTER);


        mainframe.add(east, BorderLayout.EAST);
    }

    private void createWestPanel(int width, int height, JFrame mainframe) {
        JPanel west = new JPanel();
        west.setBackground(new Color(198,160,98));
        west.setPreferredSize(new Dimension(6*width, height));
        west.setLayout(new BorderLayout());


        JPanel west_north = (JPanel) messagePopUp.getContentPane();
        west_north.setOpaque(false);
        west_north.setPreferredSize(new Dimension(5*width, 4*height));

        west.add(west_north, BorderLayout.NORTH);
        warehousePanel.setPreferredSize(new Dimension(5*width,9*height));
        west.add(warehousePanel, BorderLayout.CENTER);
        strongBoxPanel.setPreferredSize(new Dimension(5*width, 5*height));
        west.add(strongBoxPanel, BorderLayout.SOUTH);

        mainframe.add(west, BorderLayout.WEST);
    }

    private void initializeSetupPanels() {
        //onlineLoginPopUp().setOpaque(false);
        //nicknamePopUp().setOpaque(false);
        //playerNumberPopUp().setOpaque(false);
        setup.add(onlineLoginPopUp());
        setup.add(nicknamePopUp());
        setup.add(playerNumberPopUp());
    }

    private JPanel resourcesPopUp(int quantity) {
        ResourcesPopUp resourcesPopUp = new ResourcesPopUp(quantity);
        resourcesPopUp.setOpaque(false);
        resourcesPopUp.setPreferredSize(new Dimension(500, 440));
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
        playerNumberPopUp.setOpaque(false);
        playerNumberPopUp.setPreferredSize(new Dimension(500, 430));

        JLabel numberOfPlayers = new JLabel("<html>   Select the<BR>number of players</html>");
        numberOfPlayers.setFont(new Font("Monaco", Font.PLAIN, 50));
        numberOfPlayers.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfPlayers.setVerticalAlignment(SwingConstants.TOP);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setSize(420,250);
        buttonPanel.setOpaque(false);

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
        onlineLoginPopUp.setLayout(new BorderLayout(5, 10));
        onlineLoginPopUp.setPreferredSize(new Dimension(500, 440));
        onlineLoginPopUp.setOpaque(false);

        JPanel title = new JPanel();
        title.setOpaque(false);

        JPanel central = new JPanel();
        central.setOpaque(false);
        central.setLayout(new BorderLayout(5, 30));

        JPanel upperCentral = new JPanel();
        upperCentral.setOpaque(false);

        JPanel lowerCentral = new JPanel();
        lowerCentral.setOpaque(false);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);


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
        nicknamePopUp.setPreferredSize(new Dimension(500, 440));
        nicknamePopUp.setOpaque(false);

        JPanel north = new JPanel();
        north.setLayout(new FlowLayout());
        north.setOpaque(false);

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout());
        center.setOpaque(false);

        JPanel south = new JPanel();
        south.setLayout(new FlowLayout());
        south.setOpaque(false);


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
        int width = 500;
        int height = 700;
        setupFrame.setSize(width,height);
        setupFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setupFrame.setTitle("SETUP");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setupFrame.setLocation(dim.width/2-width/2, dim.height/2-4*height/7);
    }

    private void startGUI() {
        if(isOnline){
            startPanel.add(onlineLoginPopUp(), BorderLayout.SOUTH);
            //setupFrame.setContentPane(onlineLoginPopUp());
            setupFrame.setContentPane(startPanel);
            setupFrame.pack();
            setupFrame.revalidate();
            setupFrame.repaint();
            setupFrame.getContentPane().setVisible(true);
            setupFrame.setSize(500, 700);
            setupFrame.setVisible(true);
        } else {
            askNickname();
        }
    }

    @Override
    public void getPeek(String name, int faithPosition, Map<ResourceType, Integer> inventory, List<EffectType> cards, List<ResourceType> resourceTypes) {
        EnemyPlayerPanel enemyPlayerPanel;

        //If no player with that nickname is registered in the lightweight model it's added.
        if(enemyPlayerPopUp.getPlayerStateByName(name) == null) {
            enemyPlayerPanel = new EnemyPlayerPanel(name);
            enemyPlayerPopUp.addEnemyPanel(enemyPlayerPanel);

        } else {
            enemyPlayerPanel = enemyPlayerPopUp.getPlayerStateByName(name);
        }
        enemyPlayerPanel.updateEnemyPlayerPanel(faithPosition, inventory, cards, resourceTypes);
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
        startPanel.update(setup.get(1));
        setupFrame.setContentPane(startPanel);
        setupFrame.revalidate();
        setupFrame.repaint();
        setupFrame.setVisible(true);
    }

    @Override
    public void askPlayerNumber() {
        if(!isOnline) {
            notifyObserver(o -> o.onUpdateNumberOfPlayers(1));

        } else {

            setupFrame.getContentPane().removeAll();
            startPanel.update(setup.get(2));
            setupFrame.setContentPane(startPanel);
            setupFrame.revalidate();
            setupFrame.repaint();
            setupFrame.setVisible(true);

           /* startPanel.update(setup.get(2));
            setupFrame.getContentPane().removeAll();
            //setupFrame.setContentPane(setup.get(2));
            setupFrame.setContentPane(startPanel);
            setupFrame.revalidate();
            setupFrame.repaint();
            setupFrame.setVisible(true);

            */
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
        faithTrackPanel.repaint();
    }

    @Override
    public void showInvalidAction(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "ERROR", JOptionPane.ERROR_MESSAGE);
    }


    @Override
    public void askToDiscard() throws ExecutionException {
        setupFrame.getContentPane().removeAll();
        startPanel.add(leaderPanels.get(0), BorderLayout.SOUTH);
        setupFrame.setContentPane(startPanel);
        //setupFrame.setContentPane(leaderPanels.get(0));
        setupFrame.revalidate();
        setupFrame.repaint();
        setupFrame.setVisible(true);
    }

    @Override
    public void showLeaderCards(List<LeaderCard> cards) {
        if(cards.size()==4){
        //if(leaderPanels.isEmpty()){
            JPanel setupLeaderPopUp = setupLeaderPopUp(cards);
            leaderPanels.add(setupLeaderPopUp);
        }
        else{
            //printLeaders(cards);
            availableLeaderPanel.updateAvailableLeaderPanel((ArrayList<LeaderCard>) cards);
            leaderRecapPanel.updateLeaderRecapPanel((ArrayList<LeaderCard>) cards);
            productionBoardPanel.updateExtraProduction((ArrayList<LeaderCard>) cards);
        }
    }

    private JPanel setupLeaderPopUp(List<LeaderCard> available) {
       SetupLeaderPopUp setupLeaderPopUp = new SetupLeaderPopUp(available);
       setupLeaderPopUp.setPreferredSize(new Dimension(500, 450));
       //setupLeaderPopUp.setOpaque(false);
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
        messagePopUp.changeMessage(errorMessage);
    }

    @Override
    public void currentTurn(String message) {
        messagePopUp.changeMessage(message);
    }

    @Override
    public void turnEnded(String message) {
        messagePopUp.changeMessage(message);
    }

    @Override
    public void askSetupResource(int number) throws ExecutionException {
        if(number!=0) {


            setupFrame.getContentPane().removeAll();
            startPanel.update(resourcesPopUp(number));
            setupFrame.setContentPane(startPanel);
            setupFrame.revalidate();
            setupFrame.repaint();
            setupFrame.setVisible(true);
           /*
            setupFrame.getContentPane().removeAll();
            setupFrame.setContentPane(resourcesPopUp(number));
            setupFrame.revalidate();
            setupFrame.repaint();
            setupFrame.setVisible(true);

            */
        }
        else {
            messagePopUp.changeMessage("The first player doesn't get to pick any resource!");
            notifyObserver(o -> o.onUpdateSetupResource(new LinkedList<>()));
        }
    }

    @Override
    public void showMatchInfo(List<String> playingNames) {

    }

    @Override
    public void showWinMatch(String winner) {
        JOptionPane.showMessageDialog(null, winner + " won!\n", "WINNER", JOptionPane.PLAIN_MESSAGE);
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

        mainframe.revalidate();
        mainframe.repaint();
    }

    @Override
    public void printPopeFavor(int pope_favor, int current_points) {
        faithTrackPanel.updatePopeFavorPoints(current_points);
    }

    @Override
    public void printLorenzoToken(String lorenzoTokenReduced, it.polimi.ingsw.enumerations.Color color, int quantity) {
        String path = "punchboard/token_";
        if (color != null){
            path = path+"discard_";
            switch (color) {
                case GREEN:
                    path = path+"green.png";
                    break;
                case BLUE:
                    path = path+"blue.png";
                    break;
                case YELLOW:
                    path = path+"yellow.png";
                    break;
                case PURPLE:
                    path = path+"purple.png";
                    break;
            }
        }
        else{
            path = path+"move_";
            if(quantity == 1){
                path = path +"one.png";
            }
            else{
                path = path +"two.png";
            }
        }
        lorenzoTokenPanel.updateLorenzoToken(path);

    }

    @Override
    public void printLorenzoFaithTrack(int faithmarker) {
        faithTrackPanel.updateLorenzo(faithmarker);
    }

    @Override
    public void printLeaders(List<LeaderCard> leaderCards) {
        //availableLeaderPanel.updateAvailableLeaderPanel((ArrayList<LeaderCard>) leaderCards);
        //leaderRecapPanel.updateLeaderRecapPanel((ArrayList<LeaderCard>) leaderCards);
        //productionBoardPanel.updateExtraProduction((ArrayList<LeaderCard>) leaderCards);
    }

    @Override
    public void printBuffer(ArrayList<ResourceType> buffer) {
        bufferPanel.updateBufferPanel(buffer);
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
        productionBoardPanel.updateProductionBoardPanel(productionBoard);
    }

    @Override
    public void printFinalProduction(HashMap<ResourceType, Integer> input, HashMap<ResourceType, Integer> output) {
        finalProductionPanel.updateFinalProductionPanel(input, output);
    }

}
