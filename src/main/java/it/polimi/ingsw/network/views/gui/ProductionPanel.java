package it.polimi.ingsw.network.views.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ProductionPanel extends JPanel {
    private String path;
    public ProductionPanel(String card_name){
        this.path="./front/";
        this.path = path.concat(card_name);
    }
    public ProductionPanel(int index){
        path= new StringBuilder().append("./front/production_").append(index).toString();
    }

    @Override
    public void paint(Graphics g) {
        MyDrawImage(g);
    }

    private void MyDrawImage(Graphics g){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(path+".png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0,0, this.getWidth(), this.getHeight(), null);
    }
}
