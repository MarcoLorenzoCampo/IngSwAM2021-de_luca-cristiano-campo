package it.polimi.ingsw.network.views.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LorenzoTokenPanel extends JPanel {
    private String path = "./punchboard/token_back.png";

    public LorenzoTokenPanel(){
        this.setBackground(new Color(198,160,98));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintToken(g);
    }

    private void paintToken(Graphics g) {
        int width = this.getWidth()/5;
        int height = this.getHeight()/4;
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(path);
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, width/2,0, 4*width,4*height, null);
    }

    public void updateLorenzoToken(String token){
        this.path = token;
        this.repaint();
    }
}
