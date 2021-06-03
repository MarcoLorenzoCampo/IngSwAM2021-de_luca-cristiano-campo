package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.utilities.builders.ResourceBoardBuilder;
import it.polimi.ingsw.parsers.ProductionCardsParser;
import it.polimi.ingsw.parsers.ResourceMarketParser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Executor {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
    /*
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

    */
        int [] dimensions = ResourceMarketParser.parseResourceMarketDimensions();
        String[] jsonResources = ResourceMarketParser.parseResourceMarketContent();
        Collections.shuffle(Arrays.asList(jsonResources));
        ResourceType extraMarble = ResourceType.valueOf(jsonResources[dimensions[1]*dimensions[0]]);
        ResourceType[][] resourceBoard = ResourceBoardBuilder.build(dimensions, jsonResources);
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame();
        //f.setContentPane(new CardMarket(sent));
        f.setContentPane(new ResourceMarket(resourceBoard, extraMarble));
        //f.setContentPane(new ProvaPanel());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.pack();
        //f.setSize(screenSize.width,screenSize.height);
        f.setSize(600,500);
        f.repaint();
        f.setVisible(true);
    }


}

