package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LeaderRecapPanel extends JPanel {

    private ArrayList<LeaderCard> available;
    private final String path = "./front/reduced_leader_";

    public LeaderRecapPanel(){
        available = new ArrayList<>();
        this.setBackground(new Color(146, 123, 91));
    }

    public void updateLeaderRecapPanel(ArrayList<LeaderCard> available){
        this.available = available;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!available.isEmpty()){
            paintLeaders(g);
        }
    }

    private void paintLeaders(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        String item;
        int height = this.getHeight()/5;
        int y = height;
        for (LeaderCard iterator : available) {
            item = path;
            switch (iterator.getEffectType()) {
                case DISCOUNT:
                    item = item + "discount_";
                    break;
                case EXTRA_INVENTORY:
                    item = item + "inventory_";
                    break;
                case MARBLE_EXCHANGE:
                    item = item + "change_";
                    break;
                case EXTRA_PRODUCTION:
                    item = item + "production_";
                    break;
            }

            switch (iterator.getResource()) {
                case COIN:
                    item = item + "coin";
                    break;
                case SHIELD:
                    item = item + "shield";
                    break;
                case SERVANT:
                    item = item + "servant";
                    break;
                case STONE:
                    item = item + "stone";
                    break;

            }
            InputStream url = cl.getResourceAsStream(item+".jpg");
            BufferedImage img = null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if(iterator.isActive()) g.drawImage(img, 0, y, this.getWidth(),3*height/2 , null);
            y += 3*height/2;
        }
    }
}
