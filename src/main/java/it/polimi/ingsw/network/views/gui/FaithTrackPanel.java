package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.model.faithtrack.FaithTrack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class FaithTrackPanel extends JPanel {
    FaithTrack faithTrack;
    Integer[] checkpoints = {1,2,4,6,9,12,16,20};
    public FaithTrackPanel(FaithTrack track){
        this.faithTrack = track;
    }

    @Override
    public void paint(Graphics g) {
        DrawFaithTrack(g);
        DrawFaithMarker(g, faithTrack.getFaithMarker());
    }

    private void DrawFaithMarker(Graphics g, int faithMarker) {
        int width = this.getWidth()/25;
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/croce.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img,faithMarker*width,0,width, width,null);
    }

    private void DrawFaithTrack(Graphics g) {
        Color popeTile = new Color(139, 0, 0);
        Graphics2D g2d = (Graphics2D) g;
        int width = this.getWidth()/25;
        int x =0;
        int y= 0;

        for (int i = 0; i < 25; i++) {

            if(i>=5 && i<=8){
                g2d.setPaint(Color.yellow);
                g2d.fillRect(x,y,width,width);
            }

            if(i>=12 && i<=16){
                g2d.setPaint(Color.orange);
                g2d.fillRect(x,y,width,width);
            }

            if(i>=19 && i<=24){
                g2d.setPaint(Color.red);
                g2d.fillRect(x,y,width,width);
            }

            if(i%8==0 && i!=0){
                g2d.setPaint(popeTile);
                g2d.fillRect(x,y,width,width);
            }

            if(i%3==0 && i!=0){
                g2d.setPaint(Color.black);
                g2d.drawString(String.valueOf(checkpoints[(i/3)-1]),x+width/2,width-5);
            }
            g2d.setPaint(Color.black);
            g2d.setFont(new Font("Monaco", Font.BOLD, width/4));
            g2d.drawString(String.valueOf(i),x+width/2,15);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x,y,x,y+width);
            x+=width;
        }
    }

}
