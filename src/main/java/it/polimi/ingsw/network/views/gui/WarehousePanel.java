package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WarehousePanel extends JPanel {
    ArrayList<ResourceType> shelves;
    ArrayList<ResourceType> extras;
    String[] resources = {
            "./punchboard/coin.png",
            "./punchboard/shield.png",
            "./punchboard/servant.png",
            "./punchboard/stone.png",
            "./punchboard/interrogativo.png"
    };


    public WarehousePanel(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras){
        this.shelves = shelves;
        this.extras = extras;
    }

    @Override
    public void paint(Graphics g) {
        DrawWarehouse(g);
    }

    private void DrawWarehouse(Graphics g) {
        int width = this.getWidth()/7;
        int height = this.getHeight()/7;
        int x=3*width;
        int y=height/2;
        int quantity=1;
        int[] shelf_start = {0,1,3};
        for (int shelf: shelf_start) {
            DrawShelf(g, x, y,width, height, shelf, quantity);
            quantity++;
            x -= width;
            y += height;
        }
            y +=height/2;
            x+= width;
        for (ResourceType iterator : extras) {
            DrawExtraShelf(g, x, y,width, height, 2* extras.indexOf(iterator)+6, 2, iterator);
            y += 3/2*height;
        }
    }

    private  void DrawShelf(Graphics g, int x, int y,int width, int height, int shelf, int quantity){
        ClassLoader cl = this.getClass().getClassLoader();
        String item = "";

        for (int i = 0; i < quantity; i++) {
                switch (shelves.get(shelf+i)) {
                    case COIN:
                        item = resources[0];
                        break;
                    case SHIELD:
                        item = resources[1];
                        break;
                    case SERVANT:
                        item = resources[2];
                        break;
                    case STONE:
                        item = resources[3];
                        break;
                    case UNDEFINED:
                        item = resources[4];
                        break;
                }
                InputStream url = cl.getResourceAsStream(item);
                BufferedImage img = null;
                try {
                    img = ImageIO.read(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                g.drawImage(img, x, y, width, height, null);
                x+= 2*width;
            }
        }

        private void DrawExtraShelf(Graphics g_sent, int x, int y,int width, int height, int shelf, int quantity, ResourceType extra_type){
            Graphics2D g = (Graphics2D) g_sent;
            ClassLoader cl = this.getClass().getClassLoader();
            String item = "";


            switch (extra_type){
                case COIN:
                    item = resources[0];
                    break;
                case SHIELD:
                    item = resources[1];
                    break;
                case SERVANT:
                    item = resources[2];
                    break;
                case STONE:
                    item = resources[3];
                    break;
            }
            InputStream url = cl.getResourceAsStream(item);
            BufferedImage img = null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, x, y, width, height, null);
            x+=width+width/2;
            float dash1[] = {10.0f};
            g.setStroke(new BasicStroke(3,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f,  dash1, 0.0f));
            g.drawLine(x,y,x,y+height);










            x+=width/2;
            for (int i = 0; i < quantity; i++) {
                switch (shelves.get(shelf+i)) {
                    case COIN:
                        item = resources[0];
                        break;
                    case SHIELD:
                        item = resources[1];
                        break;
                    case SERVANT:
                        item = resources[2];
                        break;
                    case STONE:
                        item = resources[3];
                        break;
                    case UNDEFINED:
                        item = resources[4];
                        break;
                }
                url = cl.getResourceAsStream(item);
                img = null;
                try {
                    img = ImageIO.read(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                g.drawImage(img, x, y, width, height, null);
                x+= 2*width;
            }
        }
}
