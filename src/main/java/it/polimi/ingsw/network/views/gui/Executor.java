package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.ExtraProductionLeaderCard;
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
        ExtraProductionPanel baseProductionPanel = new ExtraProductionPanel();
        JFrame frame = new JFrame();
        frame.add(baseProductionPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        frame.pack();
        frame.setSize(1500,500);
        frame.setVisible(true);

        JButton update = new JButton("update");
        update.addActionListener(e -> {
            baseProductionPanel.updateExtraProductionPanel(
                    new ExtraProductionLeaderCard(
                            ResourceType.SERVANT,
                            2,
                            EffectType.EXTRA_PRODUCTION,
                            null,
                            null,
                            null));
        });
        frame.add(update, BorderLayout.SOUTH);
    }


}

