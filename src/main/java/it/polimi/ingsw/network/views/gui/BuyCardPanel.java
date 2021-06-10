package it.polimi.ingsw.network.views.gui;

import javax.swing.*;
import java.awt.*;

public class BuyCardPanel extends JPanel {
    private final int selectedCard;
    private final JButton[] slots;

    public BuyCardPanel(int selectedCard) {
        this.selectedCard = selectedCard;
        this.slots = new JButton[3];

        for (int i = 0; i < slots.length; i++) {
            slots[i] = new JButton("SLOT "+String.valueOf(i));
        }

        this.setLayout(new BorderLayout());
        this.add(new JLabel("CHOOSE A PRODUCTION SLOT"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new FlowLayout());
        for (JButton iterator:slots) {
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
