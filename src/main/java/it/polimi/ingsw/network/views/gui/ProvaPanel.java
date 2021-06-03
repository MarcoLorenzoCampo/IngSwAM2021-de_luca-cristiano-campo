package it.polimi.ingsw.network.views.gui;

import javax.swing.*;
import java.awt.*;

public class ProvaPanel extends JPanel {
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.red);
        int x = this.getWidth()/6;
        int y = this.getWidth()/5;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 4; i++) {
                g2d.fillOval(x,y,this.getWidth()/8,this.getWidth()/8);
                x += (this.getWidth()/8+this.getWidth()/16);
            }
            x=this.getWidth()/8;;
            y += (this.getWidth()/8+this.getWidth()/16);
        }
    }
}
