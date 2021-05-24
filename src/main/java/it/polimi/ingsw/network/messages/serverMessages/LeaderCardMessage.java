package it.polimi.ingsw.network.messages.serverMessages;


import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeaderCardMessage extends Message {

    private static final long serialVersionUID = -5649155565123346151L;


    private final int size;
    private final ArrayList<EffectType> effects;
    private final ArrayList<ResourceType> resources;
    private final ArrayList<Integer> victoryPoints;
    private final ArrayList<Boolean> active;
    private final HashMap<Integer, ResourceType> storage;
    private final HashMap<Integer, ArrayList<Color>> others;

    public LeaderCardMessage (List<LeaderCard> inModel){

        super.setMessageType(PossibleMessages.AVAILABLE_LEADERS);
        super.setSenderUsername("SERVER_MESSAGE");

        this.size = inModel.size();
        this.storage = new HashMap<>();
        this.others = new HashMap<>();
        this.effects = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.victoryPoints = new ArrayList<>();
        this.active = new ArrayList<>();

        for (LeaderCard iterator : inModel) {
            effects.add(iterator.getEffectType());
            resources.add(iterator.getResource());
            victoryPoints.add(iterator.getVictoryPoints());
            active.add(iterator.isActive());

            if(iterator.getEffectType().equals(EffectType.EXTRA_INVENTORY)){
                storage.put(inModel.indexOf(iterator), iterator.getRequirementsResource()[0].getType());
            } else {
                ArrayList<Color> colors = new ArrayList<>();
                if(iterator.getRequirementsDevCards().length == 1){
                    colors.add(iterator.getRequirementsDevCards()[0].getColor());
                } else {
                    for (DevelopmentTag innerIterator  : iterator.getRequirementsDevCards()) {
                       colors.add(innerIterator.getColor());
                    }
                }
                others.put(inModel.indexOf(iterator), colors);
            }
        }
    }

    public ArrayList<Boolean> getActive() {
        return active;
    }

    public int getSize() { return size; }

    public ArrayList<EffectType> getEffects() {
        return effects;
    }

    public ArrayList<ResourceType> getResources() {
        return resources;
    }

    public ArrayList<Integer> getVictoryPoints() {
        return victoryPoints;
    }

    public HashMap<Integer, ResourceType> getStorage() {
        return storage;
    }

    public HashMap<Integer, ArrayList<Color>> getOthers() {
        return others;
    }

}
