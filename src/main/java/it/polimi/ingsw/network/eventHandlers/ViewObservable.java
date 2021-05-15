package it.polimi.ingsw.network.eventHandlers;

import java.util.ArrayList;
import java.util.List;
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
     * Adds a list of observers.
     * @param observerList the list of observers to be added.
     */
    public void addAllObservers(List<ViewObserver> observerList) {
        observers.addAll(observerList);
    }

    /**
     * Removes an observer.
     * @param obs the observer to be removed.
     */
    public void removeObserver(ViewObserver obs) {
        observers.remove(obs);
    }

    /**
     * Removes a list of observers.
     * @param observerList the list of observers to be removed.
     */
    public void removeAllObservers(List<ViewObserver> observerList) {
        observers.removeAll(observerList);
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