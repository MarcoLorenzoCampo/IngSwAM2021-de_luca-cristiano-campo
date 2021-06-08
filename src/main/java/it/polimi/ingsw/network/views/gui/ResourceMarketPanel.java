package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class ResourceMarketPanel extends JPanel {
    JButton[] arrows;
    ResourceType[][] resourceBoard;
    ResourceType extraMarble;
    String path = "./punchboard/marble_";

    public ResourceMarketPanel(ResourceType[][] resourceBoard, ResourceType extraMarble){
        arrows = new JButton[7];
        this.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,6,0,10));
        buttons.setOpaque(false);


        for (int i = 0; i < 7; i++) {
            arrows[i] = new JButton(String.valueOf(i));
            arrows[i].setFocusable(false);
            buttons.add(arrows[i]);
        }

        this.add(buttons, BorderLayout.SOUTH);
        this.resourceBoard = resourceBoard;
        this.extraMarble = extraMarble;
    }

    public ResourceMarketPanel() {
        arrows = new JButton[7];
        this.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,6,0,10));
        buttons.setOpaque(false);


        for (int i = 0; i < 7; i++) {
            arrows[i] = new JButton(String.valueOf(i));
            arrows[i].setFocusable(false);
            buttons.add(arrows[i]);
        }

        this.add(buttons, BorderLayout.SOUTH);
        this.resourceBoard = null;
        this.extraMarble = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth()/9;
        int height = this.getHeight()/10;
        paintBackground(g);
        if(resourceBoard!=null && extraMarble!=null){
            paintMarket(g,width, height);
            paintExtraMarble(g, width, height);
        }
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
