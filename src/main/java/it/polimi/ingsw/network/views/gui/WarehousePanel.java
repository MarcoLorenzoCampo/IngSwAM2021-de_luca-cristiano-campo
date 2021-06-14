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
    JButton source_warehouse;
    ArrayList<ResourceType> shelves;
    ArrayList<ResourceType> extras;
    String[] resources = {
            "./punchboard/coin.png",
            "./punchboard/shield.png",
            "./punchboard/servant.png",
            "./punchboard/stone.png",

    };


    public WarehousePanel(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras){
        this.setLayout(new BorderLayout());
        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        source_warehouse = new JButton("SOURCE WAREHOUSE");
        source_warehouse.setVisible(true);
        source_warehouse.setFocusable(false);
        button.setOpaque(false);

        this.shelves = shelves;
        this.extras = extras;

        button.add(source_warehouse);
        this.add(button, BorderLayout.SOUTH);
    }

    public WarehousePanel() {
        shelves = new ArrayList<>();
        extras = new ArrayList<>();

        this.setLayout(new BorderLayout());
        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        source_warehouse = new JButton("SOURCE WAREHOUSE");
        source_warehouse.setVisible(true);
        source_warehouse.setFocusable(false);
        button.setOpaque(false);


        button.add(source_warehouse);
        this.add(button, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth()/5;
        int height = this.getHeight()/9;
        paintBackground(g);
        if(!shelves.isEmpty()) DrawWarehouse(g, width, height);
    }

    private void DrawWarehouse(Graphics g, int width, int height) {

        int x=5*width/2;
        int y=4*height/3;

        int quantity=1;
        int[] shelf_start = {0,1,3};
        for (int shelf: shelf_start) {
            DrawShelf(g, x, y,width, height, shelf, quantity);
            quantity++;
            x -= width/2;
            y += 17*height/10;
        }
        y-= height/3;
        for (ResourceType iterator : extras) {
            DrawExtraShelf(g, x, y,width, height, 2* extras.indexOf(iterator)+6, 2, iterator);
            y += height;
        }
    }

    private void paintBackground(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/warehouse.png");
                BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0,0, this.getWidth(),this.getHeight(), null);
    }

    private  void DrawShelf(Graphics g, int x, int y,int width, int height, int shelf, int quantity){
        ClassLoader cl = this.getClass().getClassLoader();
        String item = "";
        boolean print = true;
        for (int i = 0; i < quantity; i++) {
                switch (shelves.get(shelf+i)) {
                    case COIN:
                        print = true;
                        item = resources[0];
                        break;
                    case SHIELD:
                        print = true;
                        item = resources[1];
                        break;
                    case SERVANT:
                        print = true;
                        item = resources[2];
                        break;
                    case STONE:
                        print = true;
                        item = resources[3];
                        break;
                    case UNDEFINED:
                        print = false;
                        break;

                }
                if (print) {
                    InputStream url = cl.getResourceAsStream(item);
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    g.drawImage(img, x, y, width, height, null);
                }
                x+=width;
            }
        }

        private void DrawExtraShelf(Graphics g_sent, int x, int y,int width, int height, int shelf, int quantity, ResourceType extra_type){
            Graphics2D g = (Graphics2D) g_sent;
            ClassLoader cl = this.getClass().getClassLoader();
            String item = "";

            boolean print = true;
            switch (extra_type){
                case COIN:
                    print = true;
                    item = resources[0];
                    break;
                case SHIELD:
                    print = true;
                    item = resources[1];
                    break;
                case SERVANT:
                    print = true;
                    item = resources[2];
                    break;
                case STONE:
                    print = true;
                    item = resources[3];
                    break;
                case UNDEFINED:
                    print = false;
                    break;
            }
            if (print) {
                InputStream url = cl.getResourceAsStream(item);
                BufferedImage img = null;
                try {
                    img = ImageIO.read(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                g.drawImage(img, x, y, width, height, null);
            }
            x+=6*width/5;
            float dash1[] = {10.0f};
            g.setPaint(Color.RED);
            g.setStroke(new BasicStroke(3,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f,  dash1, 0.0f));
            g.drawLine(x,y,x,y+height);


            x+=width/5;
            for (int i = 0; i < quantity; i++) {
                switch (shelves.get(shelf+i)) {
                    case COIN:
                        print = true;
                        item = resources[0];
                        break;
                    case SHIELD:
                        print = true;
                        item = resources[1];
                        break;
                    case SERVANT:
                        print = true;
                        item = resources[2];
                        break;
                    case STONE:
                        print = true;
                        item = resources[3];
                        break;
                    case UNDEFINED:
                        print = false;
                        break;
                }
                if (print) {
                    InputStream url = cl.getResourceAsStream(item);
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    g.drawImage(img, x, y, width, height, null);
                }
                x+=4*width/3;
            }
        }

    public void updateWarehousePanel(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras){
        this.shelves = shelves;
        this.extras = extras;
        this.repaint();
    }

    public JButton getSource_warehouse() {
        return source_warehouse;
    }
}
