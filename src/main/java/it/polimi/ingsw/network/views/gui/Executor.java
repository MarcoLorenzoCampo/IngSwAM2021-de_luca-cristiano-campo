package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.parsers.ProductionCardsParser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
       sent.add(available.get(42));
       sent.add(available.get(43));
       sent.add(available.get(44));
       sent.add(available.get(45));
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame();
        f.setContentPane(new CardMarket(sent));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.pack();
        f.setSize(screenSize.width,screenSize.height);
        f.repaint();
        f.setVisible(true);
    }


}

