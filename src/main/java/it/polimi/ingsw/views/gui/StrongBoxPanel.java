package it.polimi.ingsw.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StrongBoxPanel extends JPanel {

    private Map<ResourceType, Integer> inventory;
    private JButton source_strongbox;

    public StrongBoxPanel(Map<ResourceType, Integer> inventory){
        this.inventory = inventory;
        this.setLayout(new BorderLayout());

        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        button.setOpaque(false);

        source_strongbox = new JButton("SOURCE STRONGBOX");
        source_strongbox.setFocusable(false);
        source_strongbox.setVisible(true);
        button.add(source_strongbox);
        this.add(button, BorderLayout.SOUTH);
    }

    public StrongBoxPanel() {
        inventory = new HashMap<>();
        this.setLayout(new BorderLayout());

        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        button.setOpaque(false);

        source_strongbox = new JButton("SOURCE STRONGBOX");
        source_strongbox.setFocusable(false);
        source_strongbox.setVisible(true);
        button.add(source_strongbox);
        this.add(button, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth()/10;
        int height = this.getHeight()/10;
        DrawStrongbox(g);
        if(!inventory.isEmpty()) DrawInventory(g, width, height);
    }

    private void DrawStrongbox(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("punchboard/strongbox.png");
                BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0,0, this.getWidth(),this.getHeight(), null);
    }

    private void DrawInventory(Graphics gr, int width, int height){
        Graphics2D g = (Graphics2D) gr;
        int x = width;
        int y = height;
        Color color = null;
        ClassLoader cl = this.getClass().getClassLoader();
        for (Map.Entry<ResourceType,Integer> iterator : inventory.entrySet()) {
            String item = "punchboard/";
            switch(iterator.getKey()){

                case COIN:
                    x = 5*width;
                    y = 2*height;
                    item = item.concat("coin");
                    color = Color.yellow;
                    break;

                case SHIELD:
                    x = width/4;
                    y = 2*height;
                    item = item.concat("shield");
                    color = Color.BLUE;
                    break;

                case SERVANT:
                    x = 5*width;
                    y = 6*height;
                    item = item.concat("servant");
                    color = new Color(128, 0, 128);
                    break;

                case STONE:
                    x = width/4;
                    y = 6*height;
                    item = item.concat("stone");
                    color = Color.GRAY;
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

            g.setStroke(new BasicStroke(3));
            g.setPaint(color);
            g.fillRect(x+2*width,y,3*width,2*height);
            g.setColor(Color.black);
            g.drawRect(x+2*width,y,3*width,2*height);
            g.drawImage(img, x, y, 11*width/6, 11*height/6, null);

            DrawNumber(g, x+this.getWidth()/10, y, iterator.getValue());

        }
    }

    private void DrawNumber(Graphics g, int x, int y, int number) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setFont(new Font("Monaco", Font.PLAIN, this.getHeight()/5));
        g2d.setColor(Color.black);
        g2d.drawString(String.valueOf(number),x+this.getWidth()/10+10,y+this.getHeight()/5-5);
    }

    public void updateStrongboxPanel(Map<ResourceType, Integer> inventory){
        this.inventory = inventory;
        this.repaint();
    }

    public JButton getSource_strongbox() {
        return source_strongbox;
    }
}
