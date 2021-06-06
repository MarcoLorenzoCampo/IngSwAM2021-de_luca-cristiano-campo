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
        e.setContentPane(new CardMarketPanel(sent));
        f.setContentPane(new FaithTrackPanel(track));
        ResourceMarketPanel marketPanel = new ResourceMarketPanel(resourceBoard, extraMarble);
        g.setContentPane(marketPanel);
        h.setContentPane(new EnemyPlayerPanel("mario", 4, inventory,cards));



        a.pack();
        a.setSize(250,450);
        a.repaint();
        a.setVisible(true);
/*
        e.pack();
        e.setSize(700,900);
        e.repaint();
        e.setVisible(true);

        f.pack();
        f.setSize(screenSize.width, screenSize.width/15);
        f.repaint();
        f.setVisible(true);

        g.pack();
        g.setSize(440,500);
        g.repaint();
        g.setVisible(true);

        h.pack();
        h.setSize(800,400);
        h.repaint();
        h.setVisible(true);

 */




        ResourceType[] extraMarble_1 = {ResourceType.FAITH, ResourceType.SHIELD, ResourceType.UNDEFINED,
        ResourceType.COIN, ResourceType.STONE, ResourceType.SERVANT};

        ArrayList<ResourceType> warehouse_1 = new ArrayList<>();
        warehouse_1.add(ResourceType.SHIELD);

        warehouse_1.add(ResourceType.STONE);
        warehouse_1.add(ResourceType.STONE);

        warehouse_1.add(ResourceType.SERVANT);
        warehouse_1.add(ResourceType.SERVANT);
        warehouse_1.add(ResourceType.SERVANT);

        warehouse_1.add(ResourceType.SHIELD);
        warehouse_1.add(ResourceType.UNDEFINED);


        ArrayList<ResourceType> extra_1 = new ArrayList<>();
        extra_1.add(ResourceType.SHIELD);


        JFrame prova = new JFrame();
        JButton test = new JButton();
        test.addActionListener(e_1 ->
        {
            warehousePanel.updateWarehousePanel(warehouse_1, extra_1);

        });

        prova.add(test);
        prova.setVisible(true);




    }


}

