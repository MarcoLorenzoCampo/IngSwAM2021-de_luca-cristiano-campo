package it.polimi.ingsw.model.utilities;

import java.util.ArrayList;

/**
 * Class that defines the concep of a production
 */
public class BaseProduction {

    private ArrayList<ResourceTag> inputResources;
    private ArrayList<ResourceTag> outputResources;

    public BaseProduction (){
        this.inputResources = new ArrayList<>();
        this.outputResources = new ArrayList<>();
    }

    public ArrayList<ResourceTag> getInputResources() {
        return inputResources;
    }

    public ArrayList<ResourceTag> getOutputResources() {
        return outputResources;
    }

    public void setOutputResources(ArrayList<ResourceTag> outputResources) {
        this.outputResources = outputResources;
    }

    public void setInputResources(ArrayList<ResourceTag> inputResources) {
        this.inputResources = inputResources;
    }
}
