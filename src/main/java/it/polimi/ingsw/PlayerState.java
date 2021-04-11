package it.polimi.ingsw;

public class PlayerState {

    private static boolean hasPlaceableLeaders;
    private static boolean hasActivatedProductions;
    private static boolean hasPickedResources;


    public static boolean isHasPlaceableLeaders() {
        return hasPlaceableLeaders;
    }

    public static void setHasPlaceableLeaders(boolean hasPlaceableLeaders) {
        PlayerState.hasPlaceableLeaders = hasPlaceableLeaders;
    }

    public static boolean isHasActivatedProductions() {
        return hasActivatedProductions;
    }

    public static void setHasActivatedProductions(boolean hasActivatedProductions) {
        PlayerState.hasActivatedProductions = hasActivatedProductions;
    }

    public static boolean isHasPickedResources() {
        return hasPickedResources;
    }

    public static void setHasPickedResources(boolean hasPickedResources) {
        PlayerState.hasPickedResources = hasPickedResources;
    }
}
