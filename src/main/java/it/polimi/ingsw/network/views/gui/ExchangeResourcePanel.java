package it.polimi.ingsw.network.views.gui;

import javax.swing.*;
import java.awt.*;

public class ExchangeResourcePanel extends JPanel{
    private final JButton[] resources;

    ExchangeResourcePanel(){
        resources = new JButton[4];
        this.setBackground(new Color(146, 123, 91));
        for (int i = 0; i < resources.length; i++) {
            resources[i] = new JButton();
            resources[i].setFocusable(false);
        }
        resources[0].setText("COIN");
        resources[1].setText("SHIELD");
        resources[2].setText("SERVANT");
        resources[3].setText("STONE");

        JPanel north = new JPanel();
        north.setOpaque(false);
        north.setLayout(new FlowLayout());
        north.add(new JLabel("<html>CHOOSE A RESOURCE IN WHICH TO CHANGE THE WHITE MARBLE<BR>REMEMBER: YOU HAVE TO HAVE THE LEADER POWER ACTIVE</html>"));

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new FlowLayout());
        for (JButton iterator : resources) {
            center.add(iterator);
        }

        this.add(north, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
    }

    public JButton[] getResources() {
        return resources;
    }
}
