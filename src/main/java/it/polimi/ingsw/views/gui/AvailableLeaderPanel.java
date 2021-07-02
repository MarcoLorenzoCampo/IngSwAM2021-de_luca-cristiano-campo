package it.polimi.ingsw.views.gui;

import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AvailableLeaderPanel extends JPanel {
    private ArrayList<LeaderCard> available;
    private final ArrayList<JButton> buttons;
    private final String path;


    AvailableLeaderPanel(){
        this.setLayout(new BorderLayout());

        path = "front/leader_";
        available = new ArrayList<>();

        buttons = new ArrayList<>();
        buttons.add( new JButton("ACTIVATE 0"));
        buttons.add( new JButton("ACTIVATE 1"));
        buttons.add( new JButton("DISCARD 0"));
        buttons.add( new JButton("DISCARD 1"));

        for (JButton iterator : buttons) {
            iterator.setFocusable(false);
            iterator.setVisible(true);
        }

        this.setBackground(new Color(198,160,98));

        JPanel south = new JPanel();
        south.setOpaque(false);


        south.setLayout(new GridLayout(2,2));
        for (JButton iterator: buttons) {
            south.add(iterator);
        }


        this.add(south, BorderLayout.SOUTH);
        JPanel upper_sx = new JPanel();
        upper_sx.setOpaque(false);
        upper_sx.setLayout(new FlowLayout());
        upper_sx.add(buttons.get(0));

        JPanel upper_dx = new JPanel();
        upper_dx.setOpaque(false);
        upper_dx.setLayout(new FlowLayout());
        upper_dx.add(buttons.get(1));

        JPanel lower_sx = new JPanel();
        lower_sx.setOpaque(false);
        lower_sx.setLayout(new FlowLayout());
        lower_sx.add(buttons.get(2));

        JPanel lower_dx = new JPanel();
        lower_dx.setOpaque(false);
        lower_dx.setLayout(new FlowLayout());
        lower_dx.add(buttons.get(3));

        south.add(upper_sx);
        south.add(upper_dx);
        south.add(lower_sx);
        south.add(lower_dx);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!available.isEmpty()){
            paintLeaders(g);
        }
    }

    private void paintLeaders(Graphics g) {
        int width = this.getWidth()/16;
        int height= this.getHeight()/13;
        int x;
        int y;

        if(available.size()==2) x = 3*width/2;
        else x=5*width;

        y = 3*height/2;
        String effect = " ";
        String resource = " ";
        ClassLoader cl = this.getClass().getClassLoader();
        for (LeaderCard iterator : available) {

            if (iterator.isActive()) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.red);
            }
            g.fillRect(x, y, 6 * width, 17 * height / 2);

            switch (iterator.getEffectType()) {

                case DISCOUNT:
                    effect = "discount_";
                    break;
                case EXTRA_INVENTORY:
                    effect = "inventory_";
                    break;
                case MARBLE_EXCHANGE:
                    effect = "change_";
                    break;
                case EXTRA_PRODUCTION:
                    effect = "production_";
                    break;
            }

            switch (iterator.getResource()) {

                case COIN:
                    resource = "coin";
                    break;
                case SHIELD:
                    resource = "shield";
                    break;
                case SERVANT:
                    resource = "servant";
                    break;
                case STONE:
                    resource = "stone";
                    break;
            }

            InputStream url = cl.getResourceAsStream(path+effect+resource+".png");
            BufferedImage img = null;
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            g.drawImage(img, x+width/2, y+height/2, 5*width, 15*height/2, null);
            x += 7*width;
        }
    }


    public void updateAvailableLeaderPanel(ArrayList<LeaderCard> available){
        this.available = available;
        if(this.available.size()==1){
            buttons.get(1).setVisible(false);
            buttons.get(3).setVisible(false);
            if(this.available.get(0).isActive()){
                buttons.get(0).setVisible(false);
                buttons.get(2).setVisible(false);
            }
        }
        else if (this.available.size()==0){
            for (JButton iterator : buttons) {
                iterator.setVisible(false);
            }
        }
        else {
            for (int i = 0; i < this.available.size(); i++) {
                if (this.available.get(i).isActive()) {
                    if (i == 0) {
                        buttons.get(0).setVisible(false);
                        buttons.get(2).setVisible(false);
                    } else if (i == 1) {
                        buttons.get(1).setVisible(false);
                        buttons.get(3).setVisible(false);
                    }
                }
            }
        }
        this.revalidate();
        this.repaint();
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public ArrayList<LeaderCard> getAvailable() {
        return available;
    }
}
