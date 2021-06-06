package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class StrongBoxPanel extends JPanel {

    private Map<ResourceType, Integer> inventory;

    public StrongBoxPanel(Map<ResourceType, Integer> inventory){
        this.inventory = inventory;
    }

    @Override
    public void paint(Graphics g) {
        DrawInventory(g);
    }

    private void DrawInventory(Graphics g){
        ClassLoader cl = this.getClass().getClassLoader();
        int x = this.getWidth()/10;
        int y = this.getHeight()/6;
        for (Map.Entry<ResourceType,Integer> iterator : inventory.entrySet()) {
            String item = "./punchboard/";
            switch(iterator.getKey()){
                case COIN:
                    x = 6*this.getWidth()/10;
                    y = 3*this.getHeight()/5;
                    item = item.concat("coin");
                    break;
                case SHIELD:
                    x = this.getWidth()/10;
                    y = this.getHeight()/5;
                    item = item.concat("shield");
                    break;
                case SERVANT:
                    x = 6*this.getWidth()/10;
                    y = this.getHeight()/5;
                    item = item.concat("servant");
                    break;
                case STONE:
                    x = this.getWidth()/10;
                    y = 3*this.getHeight()/5;
                    item = item.concat("stone");
                    break;
            }
            InputStream url = cl.getResourceAsStream(item+".png");
            BufferedImage img = null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, x, y, this.getWidth()/10, this.getHeight()/5, null);
            DrawNumber(g, x+this.getWidth()/10, y, iterator.getValue());

        }
    }

    private void DrawNumber(Graphics g, int x, int y, int number) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        //QUI DISEGNA L'UGUALE
        g2d.drawLine(x+10,y+this.getHeight()/10+5,x+this.getWidth()/10, y+this.getHeight()/10+5);
        g2d.drawLine(x+10,y+this.getHeight()/10+20,x+this.getWidth()/10, y+this.getHeight()/10+20);
        g2d.setFont(new Font("Monaco", Font.PLAIN, this.getHeight()/5));
        g2d.drawString(String.valueOf(number),x+this.getWidth()/10+10,y+this.getHeight()/5-5);

    }
}
