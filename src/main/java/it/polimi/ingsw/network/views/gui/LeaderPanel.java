package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class LeaderPanel extends JPanel    {
    String path;
    public LeaderPanel(String path){
        this.path = "front/";
        this.path = this.path.concat(path);
    }
    public LeaderPanel(LeaderCard leaderCard){
        path = "front/leader_";
        switch (leaderCard.getEffectType()){

            case DISCOUNT:
                this.path = this.path.concat("discount_");
                break;
            case EXTRA_INVENTORY:
                this.path = this.path.concat("inventory_");
                break;
            case MARBLE_EXCHANGE:
                this.path = this.path.concat("change_");
                break;
            case EXTRA_PRODUCTION:
                this.path = this.path.concat("production_");
                break;
        }
        switch(leaderCard.getResource()){

            case COIN:
                this.path = this.path.concat("coin");
                break;
            case SHIELD:
                this.path = this.path.concat("shield");
                break;
            case SERVANT:
                this.path = this.path.concat("servant");
                break;
            case STONE:
                this.path = this.path.concat("stone");
                break;
        }
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
