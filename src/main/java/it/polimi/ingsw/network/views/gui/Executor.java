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
        LeaderRecapPanel baseProductionPanel = new LeaderRecapPanel();
        JFrame frame = new JFrame();
        frame.add(baseProductionPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        frame.pack();
        frame.setSize(1500,500);
        frame.setVisible(true);
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add( new ExtraProductionLeaderCard(
                ResourceType.SERVANT,
                2,
                EffectType.EXTRA_PRODUCTION,
                null,
                null,
                null));
        cards.add(new ExtraProductionLeaderCard(
                ResourceType.COIN,
                2,
                EffectType.EXTRA_PRODUCTION,
                null,
                null,
                null));
        JButton update = new JButton("update");
        update.addActionListener(e -> {
            baseProductionPanel.updateLeaderRecapPanel(cards);
        });
        frame.add(update, BorderLayout.SOUTH);
    }


}

