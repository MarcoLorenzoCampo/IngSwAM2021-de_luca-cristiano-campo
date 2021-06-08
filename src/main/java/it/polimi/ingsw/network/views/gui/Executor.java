package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.builders.ResourceBoardBuilder;
import it.polimi.ingsw.parsers.ProductionCardsParser;
import it.polimi.ingsw.parsers.ResourceMarketParser;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Executor {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

       List<ProductionCard> available = ProductionCardsParser.parseProductionDeck();
       List<ProductionCard> sent = new ArrayList<>();

       sent.add(available.get(0));
       sent.add(available.get(1));
       sent.add(available.get(2));
       sent.add(available.get(3));
       sent.add(available.get(21));
       sent.add(available.get(22));
       sent.add(available.get(23));
       sent.add(available.get(24));
       sent.add(available.get(40));
       sent.add(available.get(41));
       sent.add(available.get(42));
       sent.add(available.get(43));

        int [] dimensions = ResourceMarketParser.parseResourceMarketDimensions();
        String[] jsonResources = ResourceMarketParser.parseResourceMarketContent();
        Collections.shuffle(Arrays.asList(jsonResources));
        ResourceType extraMarble = ResourceType.valueOf(jsonResources[dimensions[1]*dimensions[0]]);
        ResourceType[][] resourceBoard = ResourceBoardBuilder.build(dimensions, jsonResources);
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(1,2,5,5));


        ArrayList<EffectType> cards = new ArrayList<>();
        HashMap<ResourceType, Integer> inventory = new HashMap<>();
        inventory.put(ResourceType.COIN, 1);
        inventory.put(ResourceType.STONE, 1);
        inventory.put(ResourceType.SHIELD, 100);
        inventory.put(ResourceType.SERVANT, 100);

        FaithTrack track = new FaithTrack();
        //track.setFaithMarker(3);


        ArrayList<ResourceType> warehouse = new ArrayList<>();
        warehouse.add(ResourceType.SERVANT);

        warehouse.add(ResourceType.STONE);
        warehouse.add(ResourceType.UNDEFINED);

        warehouse.add(ResourceType.UNDEFINED);
        warehouse.add(ResourceType.UNDEFINED);
        warehouse.add(ResourceType.COIN);

        warehouse.add(ResourceType.SHIELD);
        warehouse.add(ResourceType.UNDEFINED);

        warehouse.add(ResourceType.SERVANT);
        warehouse.add(ResourceType.SERVANT);

        ArrayList<ResourceType> extra = new ArrayList<>();
        extra.add(ResourceType.SHIELD);
        extra.add(ResourceType.SERVANT);

        JFrame a = new JFrame();
        JFrame e = new JFrame();
        JFrame f = new JFrame();
        JFrame g = new JFrame();
        JFrame h = new JFrame();
        JFrame i = new JFrame();
        JFrame l = new JFrame();



        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //f.setSize(screenSize.width,screenSize.height);
        WarehousePanel warehousePanel = new WarehousePanel(warehouse, extra);
        a.setContentPane(warehousePanel);
        CardMarketPanel cardMarketPanel = new CardMarketPanel(sent);
        e.setContentPane(cardMarketPanel);
        f.setContentPane(new FaithTrackPanel(track));
        ResourceMarketPanel marketPanel = new ResourceMarketPanel(resourceBoard, extraMarble);
        g.setContentPane(marketPanel);
        h.setContentPane(new EnemyPlayerPanel("mario", 4, inventory,cards));


        JFrame mainframe = new JFrame();
        FaithTrackPanel faithTrackPanel = new FaithTrackPanel(track);
        mainframe.setSize(screenSize.width, screenSize.height);


        faithTrackPanel.setPreferredSize(new Dimension(screenSize.width/2, screenSize.height/4));
/*
        JPanel prova = new JPanel();
        prova.setSize(new Dimension(500, 500));
        prova.setBorder(BorderFactory.createEmptyBorder(0,0,0, screenSize.width/8));
        prova.setBackground(Color.RED);
        mainframe.add(prova, BorderLayout.WEST);


        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        center.add(faithTrackPanel, BorderLayout.NORTH);

        mainframe.add(center);
        mainframe.setVisible(true);


 */
        HashMap<Integer, ProductionCard> prod = new HashMap<>();
        prod.put(0,available.get(0));
        prod.put(1, available.get(1));
        prod.put(2, available.get(2));
        JFrame s = new JFrame();
        ProductionBoardPanel productionBoardPanel = new ProductionBoardPanel(prod);
        s.setContentPane(productionBoardPanel);

        HashMap<Integer, ProductionCard> prod_1 = new HashMap<>();
        prod_1.put(0,available.get(16));


        HashMap<Integer, ProductionCard> prod_2 = new HashMap<>();
        prod_2.put(0,available.get(40));


        JFrame prova = new JFrame();
        prova.setLayout(new FlowLayout());
        JButton test = new JButton("1");

        prova.setSize(500,500);


        e.setSize(1407,589);
        e.setVisible(true);

        List<ProductionCard> sent_1 = new ArrayList<>();
        sent_1.add(available.get(41));
        sent_1.add(available.get(42));
        sent_1.add(available.get(43));

        test.addActionListener(e_1 -> {
            cardMarketPanel.updateCardMarketPanel(sent_1);
        });
        prova.add(test);
        prova.setVisible(true);


    }


}

