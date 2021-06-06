package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class ResourceMarketPanel extends JPanel {
    ResourceType[][] resourceBoard;
    ResourceType extraMarble;
    String path = "./punchboard/marble_";

    public ResourceMarketPanel(ResourceType[][] resourceBoard, ResourceType extraMarble){
        this.resourceBoard = resourceBoard;
        this.extraMarble = extraMarble;
    }

    @Override
    public void paint(Graphics g) {
        int width = this.getWidth()/9;
        int height = this.getHeight()/10;
        paintBackground(g);
        paintMarket(g,width, height);
        paintExtraMarble(g, width, height);




        /*
        int x = 10;
        int y = 0;
        y +=this.getHeight()/5-25;
        DrawVerticalArrow(g,x,y);
        y +=this.getHeight()/5;
        for (int i = 0; i < 3; i++) {
            DrawMarketLine(g, i, x, y);
            y += this.getHeight()/5+10;
        }

         */
    }

    private void paintExtraMarble(Graphics g, int width, int height) {
        int x = 3*width/2;
        int y = 5*height+height/2;

        ClassLoader cl = this.getClass().getClassLoader();
        String item = "";
        switch (extraMarble){
            case COIN:
                item = "yellow";
                break;
            case SHIELD:
                item = "blue";
                break;
            case SERVANT:
                item = "purple";
                break;
            case STONE:
                item = "grey";
                break;
            case FAITH:
                item = "red";
                break;

            case UNDEFINED:
                item = "white";
                break;
        }
        InputStream url = cl.getResourceAsStream(path+item+".png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, x,y, width,height, null);
    }

    private void paintMarket(Graphics g, int width, int height) {
        int x = 5*width/2;
        int y = 2*height+height/3;
        ClassLoader cl = this.getClass().getClassLoader();
        String item = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                switch (resourceBoard[i][j]){
                    case COIN:
                        item = "yellow";
                        break;
                    case SHIELD:
                        item = "blue";
                        break;
                    case SERVANT:
                        item = "purple";
                        break;
                    case STONE:
                        item = "grey";
                        break;
                    case FAITH:
                        item = "red";
                        break;

                    case UNDEFINED:
                        item = "white";
                        break;
                }
                InputStream url = cl.getResourceAsStream(path+item+".png");
                BufferedImage img= null;
                try {
                    img = ImageIO.read(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                g.drawImage(img, x,y, width,height, null);
                x+=this.getWidth()/8;
            }
            x = 5*width/2;
            y += height;
        }

    }

    private void paintBackground(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/market.jpg");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0,0, this.getWidth(),this.getHeight(), null);
    }

    public void updateMarket(ResourceType[][] resourceBoard,ResourceType extraMarble){
        this.resourceBoard = resourceBoard;
        this.extraMarble = extraMarble;
        this.repaint();
    }
}
