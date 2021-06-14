package it.polimi.ingsw.network.views.gui;

import javax.swing.*;
import java.awt.*;

public class BuyCardPanel extends JPanel {
    private final int selectedCard;
    private final JButton[] slots;

    public BuyCardPanel(int selectedCard) {
        this.selectedCard = selectedCard;
        this.slots = new JButton[3];
        this.setBackground(new Color(146, 123, 91));

        for (int i = 0; i < slots.length; i++) {
            slots[i] = new JButton("SLOT "+i);
        }

        this.setLayout(new BorderLayout());
        JPanel north = new JPanel();
        north.setLayout( new FlowLayout());
        north.setOpaque(false);
        north.add(new JLabel("CHOOSE A PRODUCTION SLOT"));
        this.add(north, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new FlowLayout());
        for (JButton iterator:slots) {
            iterator.setFocusable(false);
            center.add(iterator);
        }
        this.add(center, BorderLayout.CENTER);
    }

    public JButton[] getSlots() {
        return slots;
    }

    public int getSelectedCard() {
        return selectedCard;
    }
}
