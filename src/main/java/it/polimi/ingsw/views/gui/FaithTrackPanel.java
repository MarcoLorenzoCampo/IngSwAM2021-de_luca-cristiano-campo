package it.polimi.ingsw.views.gui;

import it.polimi.ingsw.model.faithtrack.FaithTrack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class FaithTrackPanel extends JPanel {
    FaithTrack faithTrack;
    int popefavor;
    int lorenzo;

    public FaithTrackPanel(){
        faithTrack = null;
        lorenzo = 0;
        popefavor = 0;
    }
    public FaithTrackPanel(FaithTrack track){
        this.faithTrack = track;
        lorenzo = 0;
    }

    @Override
    public void paint(Graphics g) {
        int width = this.getWidth()/26;
        int height = this.getHeight()/5;
        DrawFaithTrack(g);
        DrawFaithMarker(g, lorenzo, width, height, false);
        if(faithTrack!=null){
            DrawFaithMarker(g, faithTrack.getFaithMarker(), width, height, true);
            switch(popefavor){
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
        String item = "punchboard/pope_favor";
        switch (tile){
            case 2:
                x += 19 * width/3;
                y += 55 * height/24;
                item = item.concat("2");
                break;
            case 3:
                x += 77 * width/6;
                y += 23 * height/24;
                item = item.concat("3");
                break;
            case 4:
                x += 20 * width;
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

    private void DrawFaithMarker(Graphics g, int faithMarker, int width, int height, boolean player) {
        int x=width;
        int y=27*height/8;

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url;

        if(player) url = cl.getResourceAsStream("punchboard/faith_marker.png");
        else url = cl.getResourceAsStream("punchboard/lorenzo_faith_marker.png");

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

            x += 2 * width +width/2;
            if (faithMarker > 4) {
                x += (faithMarker-4) *( 4*width/3);
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
        InputStream url = cl.getResourceAsStream("punchboard/faithtrack.png");
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

    public void updateLorenzo(int lorenzo){
        this.lorenzo = lorenzo;
        this.repaint();
    }

    public void updatePopeFavorPoints(int current_points){
        this.popefavor = current_points;
        this.repaint();
    }

}
