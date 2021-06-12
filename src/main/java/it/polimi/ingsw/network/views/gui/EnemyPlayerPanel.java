package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EnemyPlayerPanel extends JPanel {
    String name;
    int faith;
    Map<ResourceType, Integer> inventory;
    List<EffectType> cards;


    public EnemyPlayerPanel(String name, int faithPosition, Map<ResourceType, Integer> inventory, List<EffectType> cards){
        this.name = name;
        this.faith = faithPosition;
        this.inventory = inventory;
        this.cards = cards;
        this.setBackground(new Color(146, 123, 91));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = this.getWidth()/16;
        int height = this.getHeight()/6;
        drawStrongbox(g, width, height);
        if(!inventory.isEmpty()) DrawInventory(g, width, height);
        drawStrings(g, width, height);
        drawLeaders(g, width, height);
    }

    private void DrawInventory(Graphics gr, int global_width, int global_height) {
        Graphics2D g = (Graphics2D) gr;
        int x = 10*global_width;
        int y = global_height;
        int width = 6*global_width/10;
        int height = 6*global_height/10;

        Color color = null;
        ClassLoader cl = this.getClass().getClassLoader();
        for (Map.Entry<ResourceType,Integer> iterator : inventory.entrySet()) {
            String item = "./punchboard/";
            switch (iterator.getKey()) {

                case COIN:
                    x = 10*global_width + 5 * width;
                    y = global_height + 2 * height;
                    item = item.concat("coin");
                    color = Color.yellow;
                    break;

                case SHIELD:
                    x = 10*global_width + width / 4;
                    y = global_height + 2 * height;
                    item = item.concat("shield");
                    color = Color.BLUE;
                    break;

                case SERVANT:
                    x = 10*global_width + 5 * width;
                    y = global_height + 6 * height;
                    item = item.concat("servant");
                    color = new Color(128, 0, 128);
                    break;

                case STONE:
                    x = 10*global_width + width / 4;
                    y = global_height + 6 * height;
                    item = item.concat("stone");
                    color = Color.GRAY;
                    break;
            }

            InputStream url = cl.getResourceAsStream(item + ".png");
            BufferedImage img = null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            g.setStroke(new BasicStroke(3));
            g.setPaint(color);
            g.fillRect(x + 2 * width, y, 3 * width, 2 * height);
            g.setColor(Color.black);
            g.drawRect(x + 2 * width, y, 3 * width, 2 * height);
            g.drawImage(img, x, y, 11 * width / 6, 11 * height / 6, null);

            DrawNumber(g, x - width, y, iterator.getValue());
        }
    }
    private void DrawNumber(Graphics g, int x, int y, int number) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setFont(new Font("Monaco", Font.PLAIN, this.getHeight()/5));
        g2d.setColor(Color.black);
        g2d.drawString(String.valueOf(number),x+this.getWidth()/10+10,y+this.getHeight()/5-5);
    }

    private void drawStrongbox(Graphics g, int width, int height) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/strongbox.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 10*width,0, 6*width,6*height, null);
    }

    private void drawStrings(Graphics g, int width, int height){

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Monaco", Font.PLAIN, width));
        g.drawString("Username: "+String.valueOf(this.name),0, 3*height/2);


        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/enemy_faith.png");
                BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, width,2*height, 3*width/2,3*height/2, null);
        g.drawString(String.valueOf(this.faith), 5*width/2,25*height/8);
    }


    private void drawLeaders(Graphics g, int width, int height){

        ClassLoader cl = this.getClass().getClassLoader();
        String[] item = {
                "./front/reduced_leader_production_servant",
                "./front/reduced_leader_inventory_coin"
        };
        int x = 0;
        int y =4*height;

        for (int i = 0; i < item.length; i++) {
            InputStream url = cl.getResourceAsStream(item[i]+".jpg");
            BufferedImage img = null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, x, y,9*width/2 , 2*height, null);
            y+=9*width/2;
        }


    }
}
