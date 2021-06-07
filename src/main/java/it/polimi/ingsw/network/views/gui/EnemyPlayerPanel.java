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

        this.setLayout(new GridLayout(2,1,0,10));
        JPanel upper = new JPanel();
        this.add(upper);

        JPanel lower = new JPanel();
        lower.setLayout(new GridLayout(1,2,10,5));
        lower.add(new StrongBoxPanel(inventory));

        JPanel leader_panel = new JPanel();
        leader_panel.setLayout(new FlowLayout());
        Font font = new Font("Monaco", Font.PLAIN, 40);
        if(!cards.isEmpty()){
            for (EffectType iterator:cards) {
                JLabel label = new JLabel();
                label.setFont(font);
                label.setText(String.valueOf(iterator));
                leader_panel.add(label);
            }
        }
        lower.add(leader_panel);
        this.add(lower);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawUpperPart(g);
        drawLeaders(g);
    }

    private void drawUpperPart(Graphics g){
        int width = this.getWidth()/2;
        int height = this.getHeight()/2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Monaco", Font.PLAIN, width/11));
        g.drawString("Username: "+String.valueOf(this.name),2*width/11, height/2);


        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/enemy_faith.png");
                BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, width+2*width/11,height/4, this.getWidth()/8,height/4, null);
        g.drawString("Faithtrack: "+String.valueOf(this.faith), width+2*width/11,height/2);

    }


    private void drawLeaders(Graphics g){

        ClassLoader cl = this.getClass().getClassLoader();
        String[] item = {
                "./front/reduced_leader_production_servant",
                "./front/reduced_leader_inventory_servant"
        };
        int x = this.getWidth()/2;
        int y = this.getHeight()/2;

        int height=this.getHeight()/4;
        x += (x-2.5*height)/2;
        for (int i = 0; i < item.length; i++) {
            InputStream url = cl.getResourceAsStream(item[i]+".jpg");
            BufferedImage img = null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            g.drawImage(img, x, y, (int) ((2.5)*height), height, null);
            y+=height;
        }


    }
}
