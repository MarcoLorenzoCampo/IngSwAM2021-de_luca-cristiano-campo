package it.polimi.ingsw.views.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ProductionPanel extends JPanel {
    private String path;

    public ProductionPanel(String card_name){
        this.path="front/";
        this.path = path.concat(card_name);
        this.setVisible(true);
    }

    public ProductionPanel(int index){
        path= "front/production_" + index;
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        MyDrawImage(g);
    }

    private void MyDrawImage(Graphics g){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(path+".png");
        BufferedImage img;
        try {
            assert url != null;
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0,0, this.getWidth(), this.getHeight(), null);
    }
}
