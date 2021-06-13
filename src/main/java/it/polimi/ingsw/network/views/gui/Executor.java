package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.ExtraProductionLeaderCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
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
        HashMap<ResourceType, Integer> inventory = new HashMap<>();
        inventory.put(ResourceType.SHIELD, 100);
        inventory.put(ResourceType.COIN, 100);
        inventory.put(ResourceType.STONE, 100);
        inventory.put(ResourceType.SERVANT, 100);
        EnemyPlayerPanel baseProductionPanel = new EnemyPlayerPanel("mario");
        baseProductionPanel.updateEnemyPlayerPanel(5, inventory, new ArrayList<>(), null);
        EnemyPlayerPanel baseProductionPanel_1 = new EnemyPlayerPanel("marco");
        EnemyPlayerPanel baseProductionPanel_2 = new EnemyPlayerPanel("marzio");
        EnemyPlayerPanel baseProductionPanel_3 = new EnemyPlayerPanel("Alessandro");
        JFrame frame = new JFrame();
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(4, 1, 0,10));
        main.setBackground(new Color(150, 80, 0));
        main.add(baseProductionPanel);
        main.add(baseProductionPanel_1);
        main.add(baseProductionPanel_2);
        main.add(baseProductionPanel_3);
        frame.setContentPane(main);
        frame.revalidate();
        frame.repaint();
        frame.pack();
        frame.setSize(650,850);
        frame.setVisible(true);

        JButton update = new JButton("update");
        update.addActionListener(e -> {
        });
        //frame.add(update, BorderLayout.SOUTH);
    }


}

