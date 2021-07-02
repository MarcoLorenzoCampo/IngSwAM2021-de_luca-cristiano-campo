package it.polimi.ingsw.views.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StartPanel extends JPanel{


    public StartPanel(){
        this.setLayout(new BorderLayout());
        this.setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        paintBackground(g);
    }

    private void paintBackground(Graphics g) {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream url = cl.getResourceAsStream("punchboard/start.png");
            BufferedImage img= null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, 0,0, this.getWidth(),this.getHeight(), null);
    }

    public void update(JPanel panel){
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.validate();
        JPanel center = new JPanel();
        center.setOpaque(false);
        this.add(center, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
        this.validate();
        this.repaint();
    }
}
