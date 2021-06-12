package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FaithTrackPanel extends JPanel {
    FaithTrack faithTrack;

    public FaithTrackPanel(){
        faithTrack = null;
    }
    public FaithTrackPanel(FaithTrack track){
        this.faithTrack = track;
    }

    @Override
    public void paint(Graphics g) {
        int width = this.getWidth()/26;
        int height = this.getHeight()/5;
        DrawFaithTrack(g);
        if(faithTrack!=null){
            DrawFaithMarker(g, faithTrack.getFaithMarker(), width, height);
            switch(faithTrack.getCurrentFavorPoints()){
                case 2:
                    DrawPopeFavor(g, 2, width, height);
                    break;
                case 3:
                    DrawPopeFavor(g, 3, width, height);
                    break;
                case 4:
                    DrawPopeFavor(g, 4, width, height);
                    break;
                case 5:
                    DrawPopeFavor(g, 2, width, height);
                    DrawPopeFavor(g, 3, width, height);
                    break;
                case 6:
                    DrawPopeFavor(g, 2, width, height);
                    DrawPopeFavor(g, 4, width, height);
                    break;
                case 7:
                    DrawPopeFavor(g, 3, width, height);
                    DrawPopeFavor(g, 4, width, height);
                    break;
                case 9:
                    DrawPopeFavor(g, 2, width, height);
                    DrawPopeFavor(g, 3, width, height);
                    DrawPopeFavor(g, 4, width, height);
                    break;

            }
        }
    }

    private void DrawPopeFavor(Graphics g, int tile, int width, int height) {
        int x = 0;
        int y = 0;
        ClassLoader cl = this.getClass().getClassLoader();
        String item = "./punchboard/pope_favor";
        switch (tile){
            case 2:
                x += 19 * width/3;
                y += 55 * height/24;
                item = item.concat("2");
                break;
            case 3:
                x += 77 * width/6;
                y += 31 * height/24;
                item = item.concat("3");
                break;
            case 4:
                x += 62 * width/3;
                y += 55 * height/24;
                item = item.concat("4");
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
        g.drawImage(img, x,y, 2*width,2*height, null);
    }

    private void DrawFaithMarker(Graphics g, int faithMarker, int width, int height) {
        int x=width;
        int y=27*height/8;

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/faith_marker.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if(faithMarker>0 && faithMarker<9){
            if(faithMarker<2)x+=faithMarker*width+width/2;
            else{
                if(faithMarker>=2) {
                    x += 2 * width +width/2;
                    if (faithMarker > 4) {
                        x += (faithMarker-4) *( 4*width/3);
                    }
                }
            }
        }
        if(faithMarker>=9 && faithMarker<16){
            x+=9*width;
            if(faithMarker>11){
                x += (faithMarker-11) *( 4*width/3);
            }
        }
        if(faithMarker>=16 ){
            x+=31*width/2;
            if(faithMarker>18){
                x += (faithMarker-18) *( 4*width/3);
            }
            if(faithMarker>24){
                x += 6*( 4*width/3);
            }
        }

        if(faithMarker==3 || faithMarker==10 || faithMarker==17){
            y-=4*height/3;
        }
        if((faithMarker>=4&&faithMarker<=9)||(faithMarker>=18)){
            y-=7*height/3;
        }
        g.drawImage(img,x,y,width, height,null);
    }

    private void DrawFaithTrack(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("./punchboard/faithtrack.png");
                BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0,0, this.getWidth(),this.getHeight(), null);
    }

    public void updateFaithTrackPanel(FaithTrack faithTrack){
        this.faithTrack = faithTrack;
        this.repaint();
    }

}
