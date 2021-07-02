package it.polimi.ingsw.eventHandlers;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Class to be extended by the view and make it observable by class registered as observers.
 */
public abstract class ViewObservable {

    protected final ArrayList<ViewObserver> observers = new ArrayList<>();

    /**
     * Adds an observer.
     * @param obs the observer to be added.
     */
    public void addObserver(ViewObserver obs) {
        observers.add(obs);
    }

    /**
     * Notifies all the current observers through the lambda argument.
     *
     * @param consumer the lambda to be called on the observers.
     */
    protected void notifyObserver(Consumer<ViewObserver> consumer) {
        for (ViewObserver observer : observers) {
            consumer.accept(observer);
        }
    }
}
