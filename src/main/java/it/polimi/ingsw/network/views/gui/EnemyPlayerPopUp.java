package it.polimi.ingsw.network.views.gui;

import it.polimi.ingsw.network.views.cli.LightweightPlayerState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyPlayerPopUp extends JPanel {
    private final ArrayList<EnemyPlayerPanel> playerStates;

    public EnemyPlayerPopUp(){
        playerStates = new ArrayList<>();
        this.setBackground(new Color(64,54,40));
        this.setLayout(new GridLayout(4, 1));
    }

    public EnemyPlayerPanel getPlayerStateByName(String nickname) {
        for(EnemyPlayerPanel l : playerStates) {
            if(l.getNickname().equals(nickname)) {
                return l;
            }
        }
        return null;
    }

    public void addEnemyPanel(EnemyPlayerPanel enemyPlayerPanel){
        this.playerStates.add(enemyPlayerPanel);
        this.add(enemyPlayerPanel);
        this.repaint();
    }
}
