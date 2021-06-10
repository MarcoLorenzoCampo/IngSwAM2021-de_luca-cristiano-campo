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


        a.setContentPane(new WarehousePanel());
        e.setContentPane( new CardMarketPanel());
        f.setContentPane(new FaithTrackPanel());
        g.setContentPane(new ResourceMarketPanel());
        h.setContentPane(new EnemyPlayerPanel("mario", 4, inventory,cards));
        ProductionBoardPanel productionPanel = new ProductionBoardPanel();
        i.setContentPane(productionPanel);

        a.setSize(800, 800);
        e.setSize(800, 800);
        f.setSize(2400, 500);
        g.setSize(800, 800);
        h.setSize(800, 800);
        i.setSize(800, 800);

        //a.setVisible(true);
        //e.setVisible(true);
        //f.setVisible(true);
        //g.setVisible(true);
        //h.setVisible(true);

        i.setVisible(true);




    }


}

