package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EnemyPlayerPanel extends JPanel {
    String name;
    int faith;
    Map<ResourceType, Integer> inventory;
    List<EffectType> cards;


    public EnemyPlayerPanel(String name, int faithPosition, Map<ResourceType, Integer> inventory, List<EffectType> cards){
        this.name = name;
        this.faith = faithPosition;
        this.inventory = inventory;
        this.cards = cards;

        this.setLayout(new GridLayout(2,1,0,10));
        JPanel upper = new JPanel();
        upper.setLayout(new GridLayout(1,2,10,10));
        upper.add(new JLabel("Username: "+this.name));
        upper.add(new JLabel("Faithtrack position: "+String.valueOf(this.faith)));
        this.add(upper);

        JPanel lower = new JPanel();
        lower.setLayout(new GridLayout(1,2,10,5));
        JPanel rosso = new JPanel();
        rosso.setBackground(Color.red);

        HashMap<ResourceType, Integer> inventory_2 = new HashMap<>();
        inventory_2.put(ResourceType.COIN, 1);
        inventory_2.put(ResourceType.STONE, 1);
        inventory_2.put(ResourceType.SHIELD, 100);
        inventory_2.put(ResourceType.SERVANT, 100);
        lower.add(new StrongBoxPanel(inventory_2));
        lower.add(rosso);
        this.add(lower);
    }

}
